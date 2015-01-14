package com.nathb.torrentfinder.loader;

import android.content.Context;

import com.nathb.torrentfinder.TorrentFinderApplication;
import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.model.Show;

import java.util.List;

import javax.inject.Inject;

public class ShowListLoader extends AbstractLoader<Show> {

    @Inject ShowDao mShowDao;

    public ShowListLoader(Context context) {
        super(context);
        ((TorrentFinderApplication) context.getApplicationContext()).inject(this);
    }

    @Override
    public LoaderResult<List<Show>> loadInBackground() {
        final List<Show> shows = mShowDao.getAll();
        LoaderResult<List<Show>> result = new LoaderResult<List<Show>>();
        result.setResult(shows);
        return result;
    }
}
