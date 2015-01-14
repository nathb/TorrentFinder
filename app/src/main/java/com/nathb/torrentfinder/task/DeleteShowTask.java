package com.nathb.torrentfinder.task;

import android.content.Context;
import android.os.AsyncTask;

import com.nathb.torrentfinder.TorrentFinderApplication;
import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.model.Show;

import javax.inject.Inject;

public class DeleteShowTask extends AsyncTask<Void, Void, Void> {

    public interface OnShowDeletedListener {
        void onShowDeleted();
    }

    @Inject ShowDao mShowDao;
    private Show mShow;
    private OnShowDeletedListener mListener;

    public DeleteShowTask(Context context, Show show, OnShowDeletedListener listener) {
        ((TorrentFinderApplication) context.getApplicationContext()).inject(this);
        mShow = show;
        mListener = listener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        mShowDao.delete(String.valueOf(mShow.getShowId()));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListener.onShowDeleted();
    }
}