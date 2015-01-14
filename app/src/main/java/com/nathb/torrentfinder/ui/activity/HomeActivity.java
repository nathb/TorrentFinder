package com.nathb.torrentfinder.ui.activity;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nathb.torrentfinder.R;
import com.nathb.torrentfinder.TorrentFinderApplication;
import com.nathb.torrentfinder.loader.AbstractLoader;
import com.nathb.torrentfinder.loader.LoaderResult;
import com.nathb.torrentfinder.loader.TorrentListLoader;
import com.nathb.torrentfinder.model.TorrentDataWrapper;
import com.nathb.torrentfinder.service.TorrentCollectionService;
import com.nathb.torrentfinder.ui.TorrentLoadProgressListener;
import com.nathb.torrentfinder.ui.adapter.TorrentListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

public class HomeActivity extends AbstractListLoaderActivity<TorrentDataWrapper> {

    @Inject TorrentCollectionService mTorrentCollectionService;
    @InjectView(R.id.ProgressText) TextView mProgressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TorrentFinderApplication) getApplication()).inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected ArrayAdapter createAdapter() {
        return new TorrentListAdapter(this, R.layout.row_torrent, new ArrayList<TorrentDataWrapper>());
    }

    @Override
    protected AbstractLoader createLoader() {
        return new TorrentListLoader(this, new TorrentLoadProgressListener(mProgressText, new Handler()));
    }

    @Override
    protected boolean shouldLoadOnCreate() {
        return false;
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<TorrentDataWrapper>>> loader, LoaderResult<List<TorrentDataWrapper>> data) {
        super.onLoadFinished(loader, data);
        onLoadFinished(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_email:
                email();
                return true;
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_browse:
                browse();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void email() {
        mTorrentCollectionService.send(this);
        mProgressText.setText("Torrent links will be sent via email");
        mProgressText.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    private void refresh() {
        onLoadStarted();
        load();
    }

    private void browse() {
        startActivity(new Intent(this, ShowListActivity.class));
    }

    @Override
    protected void handleLoaderException(Exception e) {
        super.handleLoaderException(e);
    }

    private void onLoadStarted() {
        mProgressText.setText("");
        mProgressText.setVisibility(View.VISIBLE);
    }

    private void onLoadFinished(LoaderResult<List<TorrentDataWrapper>> data) {
        int count = 0;
        if (data != null && data.getResult() != null) {
            count = data.getResult().size();
        }

        if (count > 0) {
            mProgressText.setVisibility(View.GONE);
        } else {
            mProgressText.setText("No torrents found");
        }
    }
}
