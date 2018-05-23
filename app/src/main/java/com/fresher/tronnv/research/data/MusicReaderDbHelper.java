package com.fresher.tronnv.research.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MusicReaderDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MusicReaderContract.MusicEntry.TABLE_NAME + " (" +
                    MusicReaderContract.MusicEntry._ID + " INTEGER PRIMARY KEY," +
                    MusicReaderContract.MusicEntry.COLUMN_NAME_LYRICID + " INTEGER," +
                    MusicReaderContract.MusicEntry.COLUMN_NAME_TITLE + " TEXT," +
                    MusicReaderContract.MusicEntry.COLUMN_NAME_AUTHOR + " TEXT,"+
                    MusicReaderContract.MusicEntry.COLUMN_NAME_AVATAR + " TEXT,"+
                    MusicReaderContract.MusicEntry.COLUMN_NAME_LYRIC + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MusicReaderContract.MusicEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MusicReader.db";

    public MusicReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
