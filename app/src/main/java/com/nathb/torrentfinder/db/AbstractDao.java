package com.nathb.torrentfinder.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {

    private final SQLiteOpenHelper mDatabaseHelper;
    private String mTableName;
    private String mIdColumnName;
    private String[] mProjection;

    public AbstractDao(Context context, String tableName, String[] projection, String idColumnName) {
        mDatabaseHelper = new DatabaseHelper(context);
        mTableName = tableName;
        mProjection = projection;
        mIdColumnName = idColumnName;
    }

    protected abstract T getItemFromCursor(Cursor cursor);
    protected abstract ContentValues getContentValuesFromItem(T item);

    private SQLiteDatabase getWritableDatabase() {
        return mDatabaseHelper.getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        return mDatabaseHelper.getReadableDatabase();
    }

    private void close() {
        mDatabaseHelper.close();
    }

    protected Cursor query(String selection, String[] selectionArgs) {
        return getReadableDatabase().query(
                mTableName,
                mProjection,
                selection,
                selectionArgs,
                null, null, null);
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
        close();
    }

    public T get(String id) {
        T item = null;
        final Cursor cursor = query(mIdColumnName + "=?", new String[] { id });
        try {
            if (cursor != null && cursor.moveToFirst()) {
                item = getItemFromCursor(cursor);
            }
        } finally {
            closeCursor(cursor);
        }
        return item;
    }

    public List<T> getAll() {
        return getListFromCursor(query(null, null));
    }

    public void save(T item) {
        getWritableDatabase().replace(mTableName, null, getContentValuesFromItem(item));
    }

    public void delete(String id) {
        delete(mIdColumnName + "=?", new String[] { id });
    }

    protected void delete(String selection, String[] selectionArgs) {
        getWritableDatabase().delete(mTableName, selection, selectionArgs);
    }

    protected List<T> getListFromCursor(Cursor cursor) {
        List<T> list = new ArrayList<T>();
        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    list.add(getItemFromCursor(cursor));
                }
            }
        } finally {
            closeCursor(cursor);
        }
        return list;
    }

}
