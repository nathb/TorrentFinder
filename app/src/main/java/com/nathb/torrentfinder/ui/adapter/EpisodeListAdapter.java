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
import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.task.UpdateEpisodeTask;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class EpisodeListAdapter extends ArrayAdapter<Episode> {

    private LayoutInflater mInflater;
    private EpisodeOnCheckedChangedListener mOnCheckedChangedListener;

    public EpisodeListAdapter(Context context, int resourceId, List<Episode> objects) {
        super(context, resourceId, objects);
        mInflater = LayoutInflater.from(context);
        mOnCheckedChangedListener = new EpisodeOnCheckedChangedListener();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_episode, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Episode episode = getItem(position);
        holder.mTitle.setText(episode.getFormattedTitle());

        // Ensure previous listener is null to combat view recycling
        holder.mIsDownloadedCheckBox.setOnCheckedChangeListener(null);
        holder.mIsDownloadedCheckBox.setChecked(episode.isDownloaded());
        holder.mIsDownloadedCheckBox.setOnCheckedChangeListener(mOnCheckedChangedListener);
        holder.mIsDownloadedCheckBox.setTag(episode);

        return convertView;
    }

    private class EpisodeOnCheckedChangedListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            final Episode episode = (Episode) buttonView.getTag();
            episode.setDownloaded(isChecked);
            new UpdateEpisodeTask(getContext(), episode, isChecked).execute();
        }
    }

    static class ViewHolder {
        @InjectView(R.id.Title) TextView mTitle;
        @InjectView(R.id.IsDownloadedCheckBox)CheckBox mIsDownloadedCheckBox;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
