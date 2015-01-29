package com.nathb.torrentfinder.ui;

import android.os.Handler;
import android.widget.TextView;

import com.nathb.torrentfinder.loader.TorrentListLoader;
import com.nathb.torrentfinder.model.Episode;


public class TorrentLoadProgressListener implements TorrentListLoader.ProgressListener {

    private static final String PROGRESS_STARTED_FORMAT = "Searching for %s ...\n";
    private static final String PROGRESS_FINISHED_FORMAT = "Found %s torrent(s) in %s ms\n";
    private TextView mProgressTextView;
    private Handler mHandler;
    private long mTimer;

    public TorrentLoadProgressListener(TextView textView, Handler handler) {
        mProgressTextView = textView;
        mHandler = handler;
    }

    private void appendProgressTextLine(final String text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mProgressTextView.setText(mProgressTextView.getText() + text);
            }
        });
    }

    @Override
    public void onProgressStarted() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mProgressTextView.setText("");
            }
        });
    }

    @Override
    public void onEpisodeStarted(Episode episode) {
        mTimer = System.currentTimeMillis();
        appendProgressTextLine(
                String.format(PROGRESS_STARTED_FORMAT, episode.getFormattedTitle()));
    }

    @Override
    public void onEpisodeFinished(Episode episode, int torrentCount) {
        mTimer = System.currentTimeMillis() - mTimer;
        appendProgressTextLine(
                String.format(PROGRESS_FINISHED_FORMAT, torrentCount, mTimer));

    }
}
