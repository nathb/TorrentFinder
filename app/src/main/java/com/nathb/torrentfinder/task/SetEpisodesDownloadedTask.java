package com.nathb.torrentfinder.task;

import android.content.Context;
import android.os.AsyncTask;

import com.nathb.torrentfinder.TorrentFinderApplication;
import com.nathb.torrentfinder.db.EpisodeDao;
import com.nathb.torrentfinder.model.Episode;

import java.util.List;

import javax.inject.Inject;

public class SetEpisodesDownloadedTask extends AsyncTask<Void, Void, Boolean> {

    public interface SetAllEpisodesDownloadedListener {
        void onAllEpisodesDownloaded();
    }

    @Inject EpisodeDao mEpisodeDao;
    private List<Episode> mEpisodes;
    private SetAllEpisodesDownloadedListener mListener;

    public SetEpisodesDownloadedTask(Context context, List<Episode> episodes) {
        this(context, episodes, null);
    }

    public SetEpisodesDownloadedTask(Context context, List<Episode> episodes,
                                     SetAllEpisodesDownloadedListener listener) {
        ((TorrentFinderApplication) context.getApplicationContext()).inject(this);
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
