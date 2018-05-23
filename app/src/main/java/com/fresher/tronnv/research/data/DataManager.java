package com.fresher.tronnv.research.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.network.RequestLyricInterface;
import com.fresher.tronnv.research.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.fresher.tronnv.research.data.MusicReaderContract.MusicEntry;

import static com.fresher.tronnv.research.Utils.BASE_URL;

/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
@Singleton
public class DataManager {
    private List<MusicLyric> musicLyrics;
    private RequestLyricInterface requestLyricInterface;
    private MusicReaderDbHelper mDbHelper ;
    private Context context;
    @Inject
    public DataManager(){

    }
    public DataManager(Context context){
        this.context = context;
    }
    public List<MusicLyric> getMusicLyrics(){
        return musicLyrics;
    }
    public void setMusicLyrics(List<MusicLyric> lyrics){
         musicLyrics = lyrics;
    }
    public boolean isNull(){
        mDbHelper = new MusicReaderDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String count = "SELECT count(*) FROM " + MusicEntry.TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
            return false;
        }else{
            return true;
        }
    }
    public void putDataToDataBase(){
        // Gets the data repository in write mode

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(MusicEntry.TABLE_NAME,null,null);
        // Create a new map of values, where column names are the keys
        if(musicLyrics!= null) {
            for (MusicLyric musicLyric : musicLyrics) {
                ContentValues values = new ContentValues();
                values.put(MusicEntry.COLUMN_NAME_LYRICID, musicLyric.getLyricId());
                values.put(MusicEntry.COLUMN_NAME_TITLE, musicLyric.getName());
                values.put(MusicEntry.COLUMN_NAME_AUTHOR, musicLyric.getAuthor());
                values.put(MusicEntry.COLUMN_NAME_AVATAR, musicLyric.getAvatar());
                values.put(MusicEntry.COLUMN_NAME_LYRIC, musicLyric.getLyric());
                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(MusicEntry.TABLE_NAME, null, values);
            }
        }
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
                MusicEntry.COLUMN_NAME_LYRICID,
                MusicEntry.COLUMN_NAME_TITLE,
                MusicEntry.COLUMN_NAME_AUTHOR,
                MusicEntry.COLUMN_NAME_AVATAR,
                MusicEntry.COLUMN_NAME_LYRIC
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = MusicEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                MusicEntry.COLUMN_NAME_LYRICID ;

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
        Cursor cursor= getDataTable(MusicEntry.TABLE_NAME);
        List<MusicLyric> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            MusicLyric musicLyric = new MusicLyric();
            if(filter.endsWith("null")){
                musicLyric.setId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
                musicLyric.setLyricId(cursor.getInt(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_LYRICID)));
                musicLyric.setName(cursor.getString(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_TITLE)));
                musicLyric.setAuthor(cursor.getString(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_AUTHOR)));
                musicLyric.setAvatar(cursor.getString(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_AVATAR)));
                musicLyric.setLyric(cursor.getString(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_LYRIC)));
                itemIds.add(musicLyric);
            }
            else if(cursor.getString(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_TITLE)).toLowerCase().contains(filter.toLowerCase())) {
                musicLyric.setId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
                musicLyric.setLyricId(cursor.getInt(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_LYRICID)));
                musicLyric.setName(cursor.getString(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_TITLE)));
                musicLyric.setAuthor(cursor.getString(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_AUTHOR)));
                musicLyric.setAvatar(cursor.getString(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_AVATAR)));
                musicLyric.setLyric(cursor.getString(cursor.getColumnIndex(MusicEntry.COLUMN_NAME_LYRIC)));
                itemIds.add(musicLyric);
            }

        }
        while(cursor.moveToNext()) {
            long item = cursor.getLong(
                    cursor.getColumnIndexOrThrow(MusicEntry._ID));
        }
        cursor.close();
        if(itemIds.size() > 0) {
            musicLyrics = new ArrayList<>();
            musicLyrics.addAll(itemIds);
            return itemIds;
        }
        return null;
    }
    public MusicLyric getSongById(int id){
        for (MusicLyric musicLyric : musicLyrics){
            if(musicLyric.getId() == id){
                return musicLyric;
            }
        }
        return null;
    }
    //Use RxJava and Retrofit to get data from server
    public void restAPIToGetData(RetrofitClient retrofit, Context context) {
        this.context = context;
        mDbHelper = new MusicReaderDbHelper(context);
        requestLyricInterface = retrofit.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
        requestLyricInterface.register()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<MusicLyric>>(){
                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(getBaseContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Debug", ": " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        //Toast.makeText(getBaseContext(), "Get data success! ", Toast.LENGTH_SHORT).show();
                        Log.e("Debug", ": " + "Get data success! ");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MusicLyric> musicLyric) {
                        setMusicLyrics(musicLyric);
                        if(musicLyric!= null)
                            putDataToDataBase();
                        Log.i("Debug", "post submitted to API: " + musicLyric.get(0).toString());
                    }
                });
    }
}
