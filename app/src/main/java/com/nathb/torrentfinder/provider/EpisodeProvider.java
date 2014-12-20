package com.nathb.torrentfinder.provider;

import android.content.Context;
import android.util.Log;

import com.nathb.torrentfinder.db.EpisodeDao;
import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.loader.LoaderResult;
import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;
import com.nathb.torrentfinder.service.EpisodeListService;
import com.nathb.torrentfinder.service.impl.EpGuidesService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EpisodeProvider {

    private static final String TAG = EpisodeProvider.class.getSimpleName();

    private Context mContext;
    private Show mShow;
    private boolean mShowAllEpisodes;
    private int mShowId;

    public EpisodeProvider(Context context, Show show, boolean showAllEpisodes) {
        mContext = context;
        mShow = show;
        mShowAllEpisodes = showAllEpisodes;
    }

    public EpisodeProvider(Context context, int showId, boolean showAllEpisodes) {
        mContext = context;
        mShowId = showId;
        mShowAllEpisodes = showAllEpisodes;
    }

    public LoaderResult<List<Episode>> provide() {
        final LoaderResult<List<Episode>> result = new LoaderResult<List<Episode>>();

        // Get show information if required
        if (mShow == null) {
            mShow = new ShowDao(mContext).get(String.valueOf(mShowId));
            if (mShow == null) {
                result.setError(new Exception("Show not found with id: " + mShowId));
                return result;
            }
        }

        // Get list of remote episodes
        final EpisodeListService episodeListService = new EpGuidesService();
        final List<Episode> remoteEpisodes;
        try {
            remoteEpisodes = episodeListService.getEpisodes(mShow);
        } catch (IOException e) {
            Log.e(TAG, "Error getting remote episode list", e);
            result.setError(e);
            return result;
        }

        // Get list of local episodes
        final EpisodeDao episodeDao = new EpisodeDao(mContext);
        final List<Episode> localEpisodes = episodeDao.getEpisodesForShow(mShow);

        // Merge the two lists to set as the result
        result.setResult(mergeEpisodeLists(mShow, remoteEpisodes, localEpisodes));
        return result;
    }

    private List<Episode> mergeEpisodeLists(Show show, List<Episode> remoteEpisodes, List<Episode> localEpisodes) {
        final List<Episode> mergedList = new ArrayList<Episode>();

        // Add all local episodes to merged list if showing all
        if (mShowAllEpisodes) {
            mergedList.addAll(localEpisodes);
        }

        // Generate hash set of local episodes for comparison
        final Set<Episode> localEpisodeHash = new HashSet<Episode>();
        for (Episode episode : localEpisodes) {
            localEpisodeHash.add(episode);
        }

        // Iterate over remote episodes and add to merge list if they are not in local list
        for (Episode episode : remoteEpisodes) {
            // Show id is required for hashCode calculation
            episode.setShowId(show.getShowId());
            if (!localEpisodeHash.contains(episode)) {
                episode.setDownloaded(false);
                mergedList.add(episode);
            }
        }

        return mergedList;
    }

}
