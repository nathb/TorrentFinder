package com.nathb.torrentfinder.ui.activity;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nathb.torrentfinder.TorrentFinderApplication;
import com.nathb.torrentfinder.loader.AbstractLoader;
import com.nathb.torrentfinder.loader.LoaderResult;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class AbstractListLoaderActivity<T extends Comparable> extends ListActivity
        implements LoaderManager.LoaderCallbacks<LoaderResult<List<T>>> {

    private static final int LOADER_ID = 1;

    @InjectView(android.R.id.list) ListView mListView;

    @InjectView(android.R.id.progress) ProgressBar mProgressBar;

    private ArrayAdapter<T> mAdapter;

    protected abstract int getContentViewId();
    protected abstract ArrayAdapter createAdapter();
    protected abstract AbstractLoader createLoader();

    protected String getActionBarTitle() {
        return "TF";
    }
    protected boolean shouldLoadOnCreate() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.inject(this);
        mAdapter = createAdapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getActionBar().setTitle(getActionBarTitle());

        // If an existing loader has data show it
        // Else init a loader unless the activity calls for lazy loading
        final AbstractLoader loader = (AbstractLoader) getLoaderManager().getLoader(LOADER_ID);
        if (loader != null && loader.getData() != null && !loader.isStarted()) {
            onLoadFinished(loader, (LoaderResult<List<T>>) loader.getData());
        } else if (shouldLoadOnCreate()) {
            load();
        } else {
            hideSpinner();
        }
    }

    protected void load() {
        showSpinner();
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    protected LoaderResult<List<T>> getLoaderResult() {
        LoaderResult<List<T>> loaderResult = null;
        final AbstractLoader loader = (AbstractLoader) getLoaderManager().getLoader(LOADER_ID);
        if (loader != null) {
            loaderResult = loader.getData();
        }
        return loaderResult;
    }

    @Override
    public Loader<LoaderResult<List<T>>> onCreateLoader(int id, Bundle args) {
        return createLoader();
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<T>>> loader, LoaderResult<List<T>> data) {
        if (data != null) {
            final Exception e = data.getError();
            if (e == null) {
                mAdapter.clear();
                final List<T> list = data.getResult();
                Collections.sort(list);
                mAdapter.addAll(list);
            } else {
                handleLoaderException(e);
            }
        }
        hideSpinner();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<LoaderResult<List<T>>> loader) {
        mAdapter.clear();
    }

    protected ArrayAdapter<T> getAdapter() {
        return mAdapter;
    }

    protected void handleLoaderException(Exception e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    }

    private void showSpinner() {
        mProgressBar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    private void hideSpinner() {
        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }
}
