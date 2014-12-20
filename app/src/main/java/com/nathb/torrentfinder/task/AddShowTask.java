package com.nathb.torrentfinder.task;

import android.os.AsyncTask;

import com.nathb.torrentfinder.db.ShowDao;
import com.nathb.torrentfinder.model.Show;

public class AddShowTask extends AsyncTask<Void, Void, Void> {

    public interface OnShowAddedListener {
        void onShowAdded();
    }

    private ShowDao mShowDao;
    private Show mShow;
    private OnShowAddedListener mListener;

    public AddShowTask(ShowDao showDao, Show show, OnShowAddedListener listener) {
        mShowDao = showDao;
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
