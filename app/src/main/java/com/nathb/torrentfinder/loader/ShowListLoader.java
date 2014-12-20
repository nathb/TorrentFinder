package com.nathb.torrentfinder.loader;

import android.content.Context;
import android.util.Log;

import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.model.Show;

import java.util.List;

public class ShowListLoader extends AbstractLoader<Show> {

    private Context mContext;

    public ShowListLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public LoaderResult<List<Show>> loadInBackground() {
        final ShowDao showDao = new ShowDao(mContext);
        final List<Show> shows = showDao.getAll();
        LoaderResult<List<Show>> result = new LoaderResult<List<Show>>();
        result.setResult(shows);
        return result;
    }
}
