package com.nathb.torrentfinder.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.nathb.torrentfinder.TorrentFinderApplication;

import java.util.List;

public abstract class AbstractLoader<D> extends AsyncTaskLoader<LoaderResult<List<D>>> {

    private LoaderResult<List<D>> mData;

    public AbstractLoader(Context context) {
        super(context);
    }

    public LoaderResult<List<D>> getData() {
        return mData;
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(LoaderResult<List<D>> data) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            return;
        }

        mData = data;
        super.deliverResult(data);
    }


    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        mData = null;
    }

}
