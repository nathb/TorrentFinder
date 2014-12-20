package com.nathb.torrentfinder.task;

import android.os.AsyncTask;

import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.model.Show;

public class DeleteShowTask extends AsyncTask<Void, Void, Void> {

    public interface OnShowDeletedListener {
        void onShowDeleted();
    }

    private ShowDao mShowDao;
    private Show mShow;
    private OnShowDeletedListener mListener;

    public DeleteShowTask(ShowDao showDao, Show show, OnShowDeletedListener listener) {
        mShowDao = showDao;
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