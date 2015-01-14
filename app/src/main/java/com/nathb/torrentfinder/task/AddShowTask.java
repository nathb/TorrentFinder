package com.nathb.torrentfinder.task;

import android.content.Context;
import android.os.AsyncTask;

import com.nathb.torrentfinder.TorrentFinderApplication;
import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.model.Show;

import javax.inject.Inject;

public class AddShowTask extends AsyncTask<Void, Void, Void> {

    public interface OnShowAddedListener {
        void onShowAdded();
    }

    @Inject ShowDao mShowDao;
    private Show mShow;
    private OnShowAddedListener mListener;

    public AddShowTask(Context context, Show show, OnShowAddedListener listener) {
        ((TorrentFinderApplication) context.getApplicationContext()).inject(this);
        mShow = show;
        mListener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        mShowDao.save(mShow);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListener.onShowAdded();
    }
}
