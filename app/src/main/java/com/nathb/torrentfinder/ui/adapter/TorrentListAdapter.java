package com.nathb.torrentfinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nathb.torrentfinder.R;
import com.nathb.torrentfinder.TorrentFinderApplication;
import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.model.TorrentDataWrapper;
import com.nathb.torrentfinder.service.TorrentCollectionService;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TorrentListAdapter extends ArrayAdapter<TorrentDataWrapper> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ROW = 1;

    private LayoutInflater mInflater;
    private TorrentOnCheckedChangedListener mOnCheckChangedListener;
    @Inject TorrentCollectionService mTorrentCollectionService;

    public TorrentListAdapter(Context context, int textViewResourceId, List<TorrentDataWrapper> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
        mOnCheckChangedListener = new TorrentOnCheckedChangedListener();
        ((TorrentFinderApplication) getContext().getApplicationContext()).inject(this);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || !getEpisode(position).equals(getEpisode(position -1))) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_ROW;
        }
    }

    private Episode getEpisode(int position) {
        Episode episode = null;
        final TorrentDataWrapper data = getItem(position);
        if (data != null) {
            episode = data.getEpisode();
        }
        return episode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int viewType = getItemViewType(position);

        if (convertView == null) {

            if (viewType == VIEW_TYPE_HEADER) {
                convertView = mInflater.inflate(R.layout.row_torrent_header, parent, false);
                holder = new HeaderViewHolder(convertView);
            } else {
                convertView = mInflater.inflate(R.layout.row_torrent, parent, false);
                holder = new ViewHolder(convertView);
            }
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TorrentDataWrapper data = getItem(position);
        if (viewType == VIEW_TYPE_HEADER) {
            bindHeader(holder, data);
        } else {
            bindRow(holder, data);
        }

        return convertView;
    }

    private void bindHeader(ViewHolder holder, TorrentDataWrapper data) {
        bindRow(holder, data);
        final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.mShowTitle.setText(data.getShow().getTitle());
        headerViewHolder.mEpisodeTitle.setText(data.getEpisode().getFormattedTitle());
    }

    private void bindRow(ViewHolder holder, TorrentDataWrapper data) {
        final Torrent torrent = data.getTorrent();
        holder.mTitle.setText(torrent.getTitle());
        holder.mUploadedDate.setText(torrent.getUploadedDate());
        holder.mUploadedByUserName.setText(torrent.getUploadedByUserName());
        holder.mSize.setText(torrent.getSize());
        holder.mSeedersAndLeechers.setText(torrent.getFormattedSeedersAndLeechers());

        // Ensure previous listener is null to combat view recycling
        holder.mToDownloadCheckbox.setOnCheckedChangeListener(null);
        holder.mToDownloadCheckbox.setChecked(data.isCheckedForDownload());
        holder.mToDownloadCheckbox.setOnCheckedChangeListener(mOnCheckChangedListener);
        holder.mToDownloadCheckbox.setTag(data);
    }

    static class ViewHolder {
        @InjectView(R.id.Title) TextView mTitle;
        @InjectView(R.id.UploadedDate) TextView mUploadedDate;
        @InjectView(R.id.UploadedByUserName) TextView mUploadedByUserName;
        @InjectView(R.id.Size) TextView mSize;
        @InjectView(R.id.SeedersAndLeechers) TextView mSeedersAndLeechers;
        @InjectView(R.id.ToDownloadCheckbox) CheckBox mToDownloadCheckbox;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class HeaderViewHolder extends ViewHolder {
        @InjectView(R.id.ShowTitle) TextView mShowTitle;
        @InjectView(R.id.EpisodeTitle) TextView mEpisodeTitle;

        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    private class TorrentOnCheckedChangedListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            final TorrentDataWrapper data = (TorrentDataWrapper) buttonView.getTag();
            data.setCheckedForDownload(isChecked);
            if (isChecked) {
                mTorrentCollectionService.add(data);
            } else {
                mTorrentCollectionService.remove(data);
            }
        }
    }
}
