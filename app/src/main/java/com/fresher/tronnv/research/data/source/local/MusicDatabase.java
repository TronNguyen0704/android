package com.fresher.tronnv.research.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.fresher.tronnv.research.data.AppExecutors;
import com.fresher.tronnv.research.data.json.JSONManager;
import com.fresher.tronnv.research.data.source.RecordChart;
import com.fresher.tronnv.research.data.source.Track;

import java.util.List;

@Database(entities = {RecordChart.class, Track.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase{

    private static MusicDatabase instance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "music-db";
    public abstract RecordChartDao rankDao();
    public abstract TrackDao trackDao();
    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static MusicDatabase getInstance(final Context context, final AppExecutors executors){
        if(instance == null){
            synchronized (MusicDatabase.class){
                if(instance == null){
                    instance = buildDatabase(context.getApplicationContext(),executors);
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
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
                        insertData(database,ranks, tracks);
                        database.setDatabaseCreated();
                    });
            }

        }).build();
    }
    private static void insertData(final MusicDatabase database, final List<RecordChart> recordCharts, final List<Track> tracks){
        database.runInTransaction(() -> {
            database.rankDao().insertAll(recordCharts);
            database.trackDao().insertAll(tracks);
        });
    }
    private void updateDatabaseCreated(final Context context){
        if(context.getDatabasePath(DATABASE_NAME).exists()){
            setDatabaseCreated();
        }
    }
    private void  setDatabaseCreated(){
        isDatabaseCreated.postValue(true);
    }
    public LiveData<Boolean> getDatabaseCreated(){
        return isDatabaseCreated;
    }

}
