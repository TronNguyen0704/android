package com.fresher.tronnv.research.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.fresher.tronnv.research.data.json.JSONManager;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private MusicReaderDbHelper mDbHelper ;
    private Context context;
    private List<MusicLyric> musicLyrics;
    public Database(Context context){
        mDbHelper = new MusicReaderDbHelper(context);
        this.context = context;
        this.musicLyrics = new ArrayList<>();
        if(musicLyrics != null)
            this.musicLyrics.addAll(musicLyrics);
    }
    public void saveDataBase(){
        musicLyrics = JSONManager.JsonSimpleReader();//Read data from temp file
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(MusicReaderContract.MusicEntry.TABLE_NAME,null,null);
        // Create a new map of values, where column names are the keys
        if(musicLyrics!= null) {
            for (MusicLyric musicLyric : musicLyrics) {
                ContentValues values = new ContentValues();
                values.put(MusicReaderContract.MusicEntry.COLUMN_NAME_LYRICID, musicLyric.getLyricId());
                values.put(MusicReaderContract.MusicEntry.COLUMN_NAME_TITLE, musicLyric.getName());
                values.put(MusicReaderContract.MusicEntry.COLUMN_NAME_AUTHOR, musicLyric.getAuthor());
                values.put(MusicReaderContract.MusicEntry.COLUMN_NAME_AVATAR, musicLyric.getAvatar());
                values.put(MusicReaderContract.MusicEntry.COLUMN_NAME_LYRIC, musicLyric.getLyric());
                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(MusicReaderContract.MusicEntry.TABLE_NAME, null, values);
            }
        }
        db.close();
    }
    public Cursor getDataTable(String name){
        if(mDbHelper == null){
            mDbHelper = new MusicReaderDbHelper(context);
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                MusicReaderContract.MusicEntry.COLUMN_NAME_LYRICID,
                MusicReaderContract.MusicEntry.COLUMN_NAME_TITLE,
                MusicReaderContract.MusicEntry.COLUMN_NAME_AUTHOR,
                MusicReaderContract.MusicEntry.COLUMN_NAME_AVATAR,
                MusicReaderContract.MusicEntry.COLUMN_NAME_LYRIC
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = MusicReaderContract.MusicEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                MusicReaderContract.MusicEntry.COLUMN_NAME_LYRICID ;

        Cursor cursor = db.query(
                name,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        return cursor;
    }
    public List<MusicLyric> getDataFromDatabase(String filter){
        Cursor cursor= getDataTable(MusicReaderContract.MusicEntry.TABLE_NAME);
        List<MusicLyric> itemIds = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                MusicLyric musicLyric = new MusicLyric();
                if (filter.endsWith("null")) {
                    musicLyric.setId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
                    musicLyric.setLyricId(cursor.getInt(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_LYRICID)));
                    musicLyric.setName(cursor.getString(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_TITLE)));
                    musicLyric.setAuthor(cursor.getString(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_AUTHOR)));
                    musicLyric.setAvatar(cursor.getString(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_AVATAR)));
                    musicLyric.setLyric(cursor.getString(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_LYRIC)));
                    itemIds.add(musicLyric);
                } else if (cursor.getString(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_TITLE)).toLowerCase().contains(filter.toLowerCase())) {
                    musicLyric.setId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
                    musicLyric.setLyricId(cursor.getInt(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_LYRICID)));
                    musicLyric.setName(cursor.getString(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_TITLE)));
                    musicLyric.setAuthor(cursor.getString(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_AUTHOR)));
                    musicLyric.setAvatar(cursor.getString(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_AVATAR)));
                    musicLyric.setLyric(cursor.getString(cursor.getColumnIndex(MusicReaderContract.MusicEntry.COLUMN_NAME_LYRIC)));
                    itemIds.add(musicLyric);
                }

            }
//        while(cursor.moveToNext()) {
//            long item = cursor.getLong(
//                    cursor.getColumnIndexOrThrow(MusicReaderContract.MusicEntry._ID));
//        }
            cursor.close();
            if (itemIds.size() > 0) {
                musicLyrics = new ArrayList<>();
                musicLyrics.addAll(itemIds);
                return itemIds;
            }
        }finally {
            mDbHelper.getReadableDatabase().close();
        }
        return null;
    }
    public MusicLyric getSongById(int id){
        if(!isNull() && !(musicLyrics.size() >0)){
            musicLyrics.addAll(getDataFromDatabase("null"));
        }
        for (MusicLyric musicLyric : musicLyrics){
            if(musicLyric.getId() == id){
                return musicLyric;
            }
        }
        return null;
    }
    public boolean isNull(){
        mDbHelper = new MusicReaderDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String count = "SELECT count(*) FROM " + MusicReaderContract.MusicEntry.TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
            db.close();
            return false;
        }else{
            return true;
        }
    }
}
