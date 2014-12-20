package com.nathb.torrentfinder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.nathb.torrentfinder.model.Episode;
import com.nathb.torrentfinder.model.Show;

import java.util.List;

public class EpisodeDao extends AbstractDao<Episode> {

    public static final String TABLE_NAME = "episodes";
    public static class Columns {
        public static final String SHOW_ID = "show_id";
        public static final String SEASON_NUMBER = "season_number";
        public static final String EPISODE_NUMBER = "episode_number";
        public static final String TITLE = "title";
        public static final String[] ALL = { SHOW_ID, SEASON_NUMBER, EPISODE_NUMBER, TITLE };
    }

    public EpisodeDao(Context context) {
        super(context, TABLE_NAME, Columns.ALL, Columns.SHOW_ID);
    }

    public List<Episode> getEpisodesForShow(Show show) {
        final Cursor cursor = query(Columns.SHOW_ID + "=?", new String[] { String.valueOf(show.getShowId()) });
        return getListFromCursor(cursor);
    }

    public void delete(Episode episode) {
        final String selection = new StringBuilder()
                .append(Columns.SHOW_ID).append("=? and ")
                .append(Columns.SEASON_NUMBER).append("=? and ")
                .append(Columns.EPISODE_NUMBER).append("=?")
                .toString();

        final String[] selectionArgs = new String[] {
                String.valueOf(episode.getShowId()),
                String.valueOf(episode.getSeasonNumber()),
                String.valueOf(episode.getEpisodeNumber())
            };

        delete(selection, selectionArgs);
    }

    @Override
    protected Episode getItemFromCursor(Cursor cursor) {
        return new Episode(
                cursor.getInt(cursor.getColumnIndex(Columns.SHOW_ID)),
                cursor.getInt(cursor.getColumnIndex(Columns.SEASON_NUMBER)),
                cursor.getInt(cursor.getColumnIndex(Columns.EPISODE_NUMBER)),
                cursor.getString(cursor.getColumnIndex(Columns.TITLE)),
                true
        );
    }

    @Override
    protected ContentValues getContentValuesFromItem(Episode item) {
        final ContentValues values = new ContentValues();
        values.put(Columns.SHOW_ID, item.getShowId());
        values.put(Columns.SEASON_NUMBER, item.getSeasonNumber());
        values.put(Columns.EPISODE_NUMBER, item.getEpisodeNumber());
        values.put(Columns.TITLE, item.getTitle());
        return values;
    }

}
