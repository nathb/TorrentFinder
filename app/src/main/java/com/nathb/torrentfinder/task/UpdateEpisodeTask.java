package com.nathb.torrentfinder.task;

import android.content.Context;
import android.os.AsyncTask;

import com.nathb.torrentfinder.TorrentFinderApplication;
import com.nathb.torrentfinder.db.EpisodeDao;
import com.nathb.torrentfinder.model.Episode;

import javax.inject.Inject;

public class UpdateEpisodeTask extends AsyncTask<Void, Void, Void> {

    @Inject EpisodeDao mEpisodeDao;
    private Episode mEpisode;
    private boolean mShouldSave;

    public UpdateEpisodeTask(Context context, Episode episode, boolean shouldSave) {
        ((TorrentFinderApplication) context.getApplicationContext()).inject(this);
        mEpisode = episode;
        mShouldSave = shouldSave;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (mShouldSave) {
            mEpisodeDao.save(mEpisode);
        } else {
            mEpisodeDao.delete(mEpisode);
        }
        return null;
    }
}
