package com.nathb.torrentfinder.loader;

import android.content.Context;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.provider.EpisodeProvider;

import java.util.List;

public class EpisodeListLoader extends AbstractLoader<Episode> {

    private static final String TAG = EpisodeListLoader.class.getSimpleName();

    private Context mContext;
    private int mShowId;
    private boolean mShowAllEpisodes;

    public EpisodeListLoader(Context context, int showId, boolean showAllEpisodes) {
        super(context);
        mContext = context;
        mShowId = showId;
        mShowAllEpisodes = showAllEpisodes;
    }

    @Override
    public LoaderResult<List<Episode>> loadInBackground() {
        return new EpisodeProvider(mContext, mShowId, mShowAllEpisodes).provide();
    }

}
