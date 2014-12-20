package com.nathb.torrentfinder.task;

import android.os.AsyncTask;

import com.nathb.torrentfinder.db.EpisodeDao;
import com.nathb.torrentfinder.model.Episode;

public class UpdateEpisodeTask extends AsyncTask<Void, Void, Void> {

    private EpisodeDao mEpisodeDao;
    private Episode mEpisode;
    private boolean mShouldSave;

    public UpdateEpisodeTask(EpisodeDao episodeDao, Episode episode, boolean shouldSave) {
        mEpisodeDao = episodeDao;
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
