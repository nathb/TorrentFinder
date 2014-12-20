package com.nathb.torrentfinder.model;

public class Torrent implements Comparable<Torrent> {

    private static final String SEEDERS_AND_LEECHERS_FORMAT = "%s / %s";

    private String mTitle;
    private String mUploadedDate;
    private String mSize;
    private String mUploadedByUserName;
    private String mMagnetLink;
    private int mSeeders;
    private int mLeechers;

    public Torrent(String title, String uploadedDate, String size, String uploadedByUserName, String magnetLink, int seeders, int leechers) {
        mTitle = title;
        mUploadedDate = uploadedDate;
        mSize = size;
        mUploadedByUserName = uploadedByUserName;
        mMagnetLink = magnetLink;
        mSeeders = seeders;
        mLeechers = leechers;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUploadedDate() {
        return mUploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        mUploadedDate = uploadedDate;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getUploadedByUserName() {
        return mUploadedByUserName;
    }

    public void setUploadedByUserName(String uploadedByUserName) {
        mUploadedByUserName = uploadedByUserName;
    }

    public String getMagnetLink() {
        return mMagnetLink;
    }

    public String getFormattedSeedersAndLeechers() {
        return String.format(SEEDERS_AND_LEECHERS_FORMAT, mSeeders, mLeechers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != Torrent.class) return false;

        Torrent torrent = (Torrent) o;

        if (mMagnetLink != null ? !mMagnetLink.equals(torrent.mMagnetLink) : torrent.mMagnetLink != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mMagnetLink != null ? mMagnetLink.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Torrent{" +
                "mTitle='" + mTitle + '\'' +
                ", mUploadedDate='" + mUploadedDate + '\'' +
                ", mSize='" + mSize + '\'' +
                ", mUploadedByUserName='" + mUploadedByUserName + '\'' +
                ", mMagnetLink='" + mMagnetLink + '\'' +
                ", mSeeders=" + mSeeders +
                ", mLeechers=" + mLeechers +
                '}';
    }

    @Override
    public int compareTo(Torrent another) {
        return another != null ? another.mSeeders - mSeeders : 1;
    }
}
