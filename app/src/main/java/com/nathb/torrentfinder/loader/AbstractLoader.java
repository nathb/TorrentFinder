package com.nathb.torrentfinder.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public abstract class AbstractLoader<D> extends AsyncTaskLoader<LoaderResult<List<D>>> {

    private LoaderResult<List<D>> mData;

    public AbstractLoader(Context context) {
        super(context);
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
        mData = data;
        if (isStarted()) {
            // If the Loader is currently started, we can immediately deliver its results.
            super.deliverResult(data);
        }
    }


    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();

        if (mData != null) {
            mData = null;
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        mData = null;
    }

}
