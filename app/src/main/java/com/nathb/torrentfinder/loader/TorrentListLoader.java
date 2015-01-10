package com.nathb.torrentfinder.loader;

import android.content.Context;

import com.nathb.torrentfinder.config.Config;
import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.model.Torrent;
import com.nathb.torrentfinder.model.TorrentDataWrapper;
import com.nathb.torrentfinder.provider.EpisodeProvider;
import com.nathb.torrentfinder.service.TorrentService;
import com.nathb.torrentfinder.service.exception.TorrentResponseException;
import com.nathb.torrentfinder.service.factory.TorrentServiceFactory;

import java.util.ArrayList;
import java.util.List;

public class TorrentListLoader extends AbstractLoader<TorrentDataWrapper> {

    public interface ProgressListener {
        void onEpisodeStarted(Episode episode);
        void onEpisodeFinished(Episode episode, int torrentCount);
    }

    private Context mContext;
    private ProgressListener mListener;
    private boolean mShouldQuerySingleEpisode;

    public TorrentListLoader(Context context, ProgressListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        mShouldQuerySingleEpisode = Config.getSingleEpisodePreference(context);
    }

    @Override
    public LoaderResult<List<TorrentDataWrapper>> loadInBackground() {
        final List<TorrentDataWrapper> latestTorrents = new ArrayList<TorrentDataWrapper>();
        final LoaderResult<List<TorrentDataWrapper>> result = new LoaderResult<List<TorrentDataWrapper>>();
        result.setResult(latestTorrents);

        final TorrentService torrentService = TorrentServiceFactory.getTorrentService(getContext());

        // Get all shows from database
        final ShowDao showDao = new ShowDao(mContext);
        final List<Show> shows = showDao.getAll();

        for (Show show : shows) {

            // Get undownloaded episodes for show
            final EpisodeProvider provider = new EpisodeProvider(mContext, show, false);
            final LoaderResult<List<Episode>> episodesResult = provider.provide();

            final List<Episode> episodes = episodesResult.getResult();
            final Exception error = episodesResult.getError();

            if (error != null) {
                result.setError(error);
                return result;
            }

            // Get torrents for episodes
            for (Episode episode : episodes) {

                if (shouldFilter(episode)) {
                    continue;
                }

                mListener.onEpisodeStarted(episode);

                final List<Torrent> torrents;
                try {
                    torrents = torrentService.getTorrents(show, episode);
                } catch (TorrentResponseException e) {
                    result.setError(e);
                    return result;
                }

                // Add torrents to result list
                final int torrentsFound = torrents.size();

                // If there were not enough torrents to reach the limit,
                // it is likely the episode has not been released yet
                if (torrentsFound == TorrentService.LIMIT) {
                    for (Torrent torrent : torrents) {
                        latestTorrents.add(new TorrentDataWrapper(show, episode, torrent));
                    }
                }

                mListener.onEpisodeFinished(episode, torrents.size());

                if (mShouldQuerySingleEpisode) {
                    break;
                }
            }
        }

        return result;
    }

    private boolean shouldFilter(Episode episode) {
        final String title = episode.getTitle().toLowerCase();
        return title.contains("season") &&
                title.contains("episode");
    }

}
