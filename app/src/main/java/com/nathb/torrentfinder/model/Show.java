package com.nathb.torrentfinder.model;

public class Show implements Comparable<Show> {

    private Integer mShowId;
    private String mTitle;
    private String mTorrentSearchTerm;
    private String mEpisodeListSearchTerm;

    public Show(String title, String torrentSearchTerm, String episodeListSearchTerm) {
        this(null, title, torrentSearchTerm,episodeListSearchTerm);
    }

    public Show(Integer showId, String title, String torrentSearchTerm, String episodeListSearchTerm) {
        mShowId = showId;
        mTitle = title;
        mTorrentSearchTerm = torrentSearchTerm;
        mEpisodeListSearchTerm = episodeListSearchTerm;
    }

    public Integer getShowId() {
        return mShowId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getEpisodeListSearchTerm() {
        return mEpisodeListSearchTerm;
    }

    public String getTorrentSearchTerm() {
        return mTorrentSearchTerm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != Show.class) return false;

        Show show = (Show) o;

        if (mShowId != null ? !mShowId.equals(show.mShowId) : show.mShowId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mShowId != null ? mShowId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public int compareTo(Show another) {
        return another != null ? mTitle.compareTo(another.mTitle) : 1;
    }

}
