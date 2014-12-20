package com.nathb.torrentfinder.model;

public class TorrentDataWrapper implements Comparable<TorrentDataWrapper> {

    private Show mShow;
    private Episode mEpisode;
    private Torrent mTorrent;
    private boolean mIsCheckedForDownload;

    public TorrentDataWrapper(Show show, Episode episode, Torrent torrent) {
        mShow = show;
        mEpisode = episode;
        mTorrent = torrent;
    }

    public Show getShow() {
        return mShow;
    }

    public Episode getEpisode() {
        return mEpisode;
    }

    public Torrent getTorrent() {
        return mTorrent;
    }

    public boolean isCheckedForDownload() {
        return mIsCheckedForDownload;
    }

    public void setCheckedForDownload(boolean isCheckedForDownload) {
        mIsCheckedForDownload = isCheckedForDownload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != TorrentDataWrapper.class) return false;

        TorrentDataWrapper that = (TorrentDataWrapper) o;

        if (mEpisode != null ? !mEpisode.equals(that.mEpisode) : that.mEpisode != null)
            return false;
        if (mShow != null ? !mShow.equals(that.mShow) : that.mShow != null) return false;
        if (mTorrent != null ? !mTorrent.equals(that.mTorrent) : that.mTorrent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mShow != null ? mShow.hashCode() : 0;
        result = 31 * result + (mEpisode != null ? mEpisode.hashCode() : 0);
        result = 31 * result + (mTorrent != null ? mTorrent.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(TorrentDataWrapper another) {
        if (another == null) {
            return 1;
        }

        // Compare by show
        final int showComparison = mShow.compareTo(another.mShow);
        if (showComparison != 0) {
            return showComparison;
        }

        // If shows are equal, compare by reverse episode order (ascending)
        final int episodeComparison = mEpisode.compareTo(another.mEpisode);
        if (episodeComparison != 0) {
            return -episodeComparison;
        }

        // If shows and episodes are equal, compare by torrent
        return mTorrent.compareTo(another.mTorrent);
    }
}
