package com.nathb.torrentfinder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TorrentFinder.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createShowsTable(db);
        insertShows(db);
        createDownloadedEpisodesTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /*
     * SHOWS table setup
     */
    private void createShowsTable(SQLiteDatabase db) {
        final StringBuilder sql = new StringBuilder("create table if not exists ")
                .append(ShowDao.TABLE_NAME).append(" ( ")
                .append(ShowDao.Columns.SHOW_ID).append(" integer primary key autoincrement not null, ")
                .append(ShowDao.Columns.TITLE).append(" text not null, ")
                .append(ShowDao.Columns.TORRENT_SEARCH_TERM).append(" text not null, ")
                .append(ShowDao.Columns.EPISODE_LIST_SEARCH_TERM).append(" text not null);");
        db.execSQL(sql.toString());
    }

    private void insertShows(SQLiteDatabase db) {
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("Game of Thrones", "Game of Thrones", "GameofThrones"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("New Girl", "New Girl", "NewGirl"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("Modern Family", "Modern Family", "ModernFamily"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("Parks and Recreation", "Parks and Recreation", "ParksandRecreation"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("Revenge", "Revenge", "Revenge"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("Suits", "Suits", "Suits"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("Silicon Valley", "Silicon Valley", "SiliconValley"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("The Big Bang Theory", "The Big Bang Theory", "BigBangTheory"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("QI", "QI", "QI"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("Once Upon a Time", "Once Upon a Time", "OnceUponaTime"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("Archer", "Archer", "Archer"));
        db.insert(ShowDao.TABLE_NAME, null, generateShowContentValues("Top Gear", "Top Gear", "TopGear"));
    }

    private ContentValues generateShowContentValues(String title, String torrentSearchTerm, String episodeListSearchTerm) {
        final ContentValues values = new ContentValues(3);
        values.put(ShowDao.Columns.TITLE, title);
        values.put(ShowDao.Columns.TORRENT_SEARCH_TERM, torrentSearchTerm);
        values.put(ShowDao.Columns.EPISODE_LIST_SEARCH_TERM, episodeListSearchTerm);
        return values;
    }

    /*
     * EPISODES table setup
     */

    private void createDownloadedEpisodesTable(SQLiteDatabase db) {
        final StringBuilder sql = new StringBuilder("create table if not exists ")
                .append(EpisodeDao.TABLE_NAME).append(" ( ")
                .append(EpisodeDao.Columns.SHOW_ID).append(" integer not null, ")
                .append(EpisodeDao.Columns.SEASON_NUMBER).append(" integer not null, ")
                .append(EpisodeDao.Columns.EPISODE_NUMBER).append(" integer not null, ")
                .append(EpisodeDao.Columns.TITLE).append(" text not null, ")
                .append(" primary key ( ").append(EpisodeDao.Columns.SHOW_ID).append(", ")
                .append(EpisodeDao.Columns.SEASON_NUMBER).append(", ")
                .append(EpisodeDao.Columns.EPISODE_NUMBER).append("));");
        db.execSQL(sql.toString());
    }

}
