package com.nathb.torrentfinder.model;

import com.nathb.torrentfinder.util.FormatUtil;

public class Episode implements Comparable<Episode> {

    private static final String TITLE_FORMAT = "S%sE%s - %s";

    private Integer mShowId;
    private Integer mSeasonNumber;
    private Integer mEpisodeNumber;
    private String mTitle;
    private Boolean mIsDownloaded;

    public Episode(Integer seasonNumber, Integer episodeNumber, String title) {
        this(null, seasonNumber, episodeNumber, title, null);
    }

    public Episode(Integer showId, Integer seasonNumber, Integer episodeNumber, String title, Boolean isDownloaded) {
        mShowId = showId;
        mSeasonNumber = seasonNumber;
        mEpisodeNumber = episodeNumber;
        mTitle = title;
        mIsDownloaded = isDownloaded;
    }

    public Integer getShowId() {
        return mShowId;
    }

    public void setShowId(Integer showId) {
        mShowId = showId;
    }

    public Integer getSeasonNumber() {
        return mSeasonNumber;
    }

    public Integer getEpisodeNumber() {
        return mEpisodeNumber;
    }

    public String getTitle() {
        return mTitle;
    }

    public Boolean isDownloaded() {
        return mIsDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        mIsDownloaded = downloaded;
    }

    public String getFormattedTitle() {
        return String.format(TITLE_FORMAT,
                FormatUtil.getFormattedNumber(mSeasonNumber),
                FormatUtil.getFormattedNumber(mEpisodeNumber),
                mTitle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != Episode.class) return false;

        Episode episode = (Episode) o;

        if (mEpisodeNumber != null ? !mEpisodeNumber.equals(episode.mEpisodeNumber) : episode.mEpisodeNumber != null)
            return false;
        if (mSeasonNumber != null ? !mSeasonNumber.equals(episode.mSeasonNumber) : episode.mSeasonNumber != null)
            return false;
        if (mShowId != null ? !mShowId.equals(episode.mShowId) : episode.mShowId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mShowId != null ? mShowId.hashCode() : 0;
        result = 31 * result + (mSeasonNumber != null ? mSeasonNumber.hashCode() : 0);
        result = 31 * result + (mEpisodeNumber != null ? mEpisodeNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "mShowId=" + mShowId +
                ", mSeasonNumber=" + mSeasonNumber +
                ", mEpisodeNumber=" + mEpisodeNumber +
                ", mTitle='" + mTitle + '\'' +
                ", mIsDownloaded=" + mIsDownloaded +
                '}';
    }

    @Override
    public int compareTo(Episode another) {
        if (another == null) {
            return 1;
        }

        return mSeasonNumber == another.mSeasonNumber
                ? another.mEpisodeNumber - mEpisodeNumber
                : another.mSeasonNumber - mSeasonNumber;
    }
}
