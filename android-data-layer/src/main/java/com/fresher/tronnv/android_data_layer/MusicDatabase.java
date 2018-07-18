package com.fresher.tronnv.android_data_layer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.fresher.tronnv.android_data_layer.base.MusicDao;
import com.fresher.tronnv.android_data_layer.base.RecordChartDao;
import com.fresher.tronnv.android_data_layer.base.TrackDao;
import com.fresher.tronnv.android_data_layer.json.JSONManager;
import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;

import java.util.List;
@Database(entities = {RecordChart.class, Track.class, MusicLyric.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase {
    private static MusicDatabase sInstance;
    @VisibleForTesting
    static final String DATABASE_NAME = "music-db";
    public abstract RecordChartDao rankDao();
    public abstract TrackDao trackDao();
    public abstract MusicDao musicDao();
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static MusicDatabase getInstance(final Context context, final AppExecutors executors){
        if(sInstance == null){
            synchronized (MusicDatabase.class){
                if(sInstance == null){
                    sInstance = buildDatabase(context.getApplicationContext(),executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }
    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }
    private static MusicDatabase buildDatabase(final Context context, final AppExecutors appExecutors){
        return Room.databaseBuilder(context, MusicDatabase.class,DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        appExecutors.getDiskIO().execute(() -> {
                            addDelay();
                            MusicDatabase database = MusicDatabase.getInstance(context,appExecutors);
                            List<RecordChart> ranks = JSONManager.JsonRecordChartReader();
                            List<Track> tracks = JSONManager.JsonTrackReader();
                            List<MusicLyric> musics = JSONManager.JsonSimpleReader();
                            insertData(database, ranks, tracks, musics);
                            database.setDatabaseCreated();
                        });
                    }

                }).build();
    }
    private static void insertData(final MusicDatabase database, final List<RecordChart> recordCharts, final List<Track> tracks, final List<MusicLyric> musicLyrics){
        database.runInTransaction(() -> {
            if(recordCharts!= null)
                database.rankDao().insertAll(recordCharts);
            if(tracks!= null)
                database.trackDao().insertAll(tracks);
            if(musicLyrics!= null)
                database.musicDao().insertAll(musicLyrics);
        });
    }
    private void updateDatabaseCreated(final Context context){
        if(context.getDatabasePath(DATABASE_NAME).exists()){
            setDatabaseCreated();
        }
    }
    private void  setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }
    public LiveData<Boolean> getDatabaseCreated(){
        return mIsDatabaseCreated;
    }
}
