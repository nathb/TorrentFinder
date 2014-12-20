package com.nathb.torrentfinder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.nathb.torrentfinder.model.Show;

public class ShowDao extends AbstractDao<Show> {

    static final String TABLE_NAME = "shows";
    static class Columns {
        public static final String SHOW_ID = "show_id";
        public static final String TITLE = "title";
        public static final String TORRENT_SEARCH_TERM = "torrent_search_term";
        public static final String EPISODE_LIST_SEARCH_TERM = "episode_list_search_term";
        public static final String[] ALL = { SHOW_ID, TITLE, TORRENT_SEARCH_TERM, EPISODE_LIST_SEARCH_TERM };
    }

    public ShowDao(Context context) {
        super(context, TABLE_NAME, Columns.ALL, Columns.SHOW_ID);
    }

    @Override
    protected Show getItemFromCursor(Cursor cursor) {
        return new Show(
                cursor.getInt(cursor.getColumnIndex(Columns.SHOW_ID)),
                cursor.getString(cursor.getColumnIndex(Columns.TITLE)),
                cursor.getString(cursor.getColumnIndex(Columns.TORRENT_SEARCH_TERM)),
                cursor.getString(cursor.getColumnIndex(Columns.EPISODE_LIST_SEARCH_TERM))
        );
    }

    @Override
    protected ContentValues getContentValuesFromItem(Show item) {
        final ContentValues values = new ContentValues();
        if (item.getShowId() != null) {
            values.put(Columns.SHOW_ID, item.getShowId());
        }
        values.put(Columns.TITLE, item.getTitle());
        values.put(Columns.TORRENT_SEARCH_TERM, item.getTorrentSearchTerm());
        values.put(Columns.EPISODE_LIST_SEARCH_TERM, item.getEpisodeListSearchTerm());
        return values;
    }
}
