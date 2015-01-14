package com.nathb.torrentfinder.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.nathb.torrentfinder.R;
import com.nathb.torrentfinder.loader.AbstractLoader;
import com.nathb.torrentfinder.loader.EpisodeListLoader;
import com.nathb.torrentfinder.loader.LoaderResult;
import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.task.SetEpisodesDownloadedTask;
import com.nathb.torrentfinder.ui.adapter.EpisodeListAdapter;

import java.util.ArrayList;
import java.util.List;

public class EpisodeListActivity extends AbstractListLoaderActivity<Episode> {

    public static final String EXTRA_SHOW_ID = "EXTRA_SHOW_ID";
    public static final String EXTRA_SHOW_TITLE = "EXTRA_SHOW_TITLE";
    private int mShowId;
    private String mShowTitle;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_episode_list;
    }

    @Override
    protected ArrayAdapter createAdapter() {
        return new EpisodeListAdapter(this, R.layout.row_episode, new ArrayList<Episode>());
    }

    @Override
    protected AbstractLoader createLoader() {
        return new EpisodeListLoader(this, mShowId, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShowId = getIntent().getIntExtra(EXTRA_SHOW_ID, -1);
        mShowTitle = getIntent().getStringExtra(EXTRA_SHOW_TITLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_episode_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_set_all_downloaded:
                setAllDownloaded();
                return true;
            case R.id.action_refresh:
                load();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected String getActionBarTitle() {
        return "TF - " + mShowTitle;
    }

    private void setAllDownloaded() {
        final LoaderResult<List<Episode>> loaderResult = getLoaderResult();
        if (loaderResult != null && loaderResult.getResult() != null && loaderResult.getError() == null) {
            new SetEpisodesDownloadedTask(
                    this, loaderResult.getResult(), new SetAllEpisodesDownloadedListener())
                    .execute();
        }
    }

    private class SetAllEpisodesDownloadedListener implements SetEpisodesDownloadedTask.SetAllEpisodesDownloadedListener {

        @Override
        public void onAllEpisodesDownloaded() {
            getAdapter().notifyDataSetChanged();
        }
    }
}
