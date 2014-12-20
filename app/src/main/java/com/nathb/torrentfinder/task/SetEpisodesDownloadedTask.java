package com.nathb.torrentfinder.task;

import android.os.AsyncTask;

import com.nathb.torrentfinder.db.EpisodeDao;
import com.nathb.torrentfinder.model.Episode;

import java.util.List;

public class SetEpisodesDownloadedTask extends AsyncTask<Void, Void, Boolean> {

    public interface SetAllEpisodesDownloadedListener {
        void onAllEpisodesDownloaded();
    }

    private EpisodeDao mEpisodeDao;
    private List<Episode> mEpisodes;
    private SetAllEpisodesDownloadedListener mListener;

    public SetEpisodesDownloadedTask(EpisodeDao episodeDao, List<Episode> episodes) {
        this(episodeDao, episodes, null);
    }

    public SetEpisodesDownloadedTask(EpisodeDao episodeDao, List<Episode> episodes,
                                     SetAllEpisodesDownloadedListener listener) {
        mEpisodeDao = episodeDao;
        mEpisodes = episodes;
        mListener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean episodesUpdated = false;
        for (Episode episode : mEpisodes) {
            if (!episode.isDownloaded()) {
                episode.setDownloaded(true);
                mEpisodeDao.save(episode);
            }
            episodesUpdated = true;
        }
        return episodesUpdated;
    }

    @Override
    protected void onPostExecute(Boolean episodesUpdated) {
        if (episodesUpdated && mListener != null) {
            mListener.onAllEpisodesDownloaded();
        }
    }
}
