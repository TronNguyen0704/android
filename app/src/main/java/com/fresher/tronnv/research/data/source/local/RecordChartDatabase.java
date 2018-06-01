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

import java.util.List;

@Database(entities = {RecordChart.class}, version = 1)
public abstract class RecordChartDatabase extends RoomDatabase{

    private static RecordChartDatabase instance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "record-chart-db";
    public abstract RecordChartDao rankDao();
    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static RecordChartDatabase getInstance(final Context context, final AppExecutors executors){
        if(instance == null){
            synchronized (RecordChartDatabase.class){
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
    private static RecordChartDatabase buildDatabase(final Context context, final AppExecutors appExecutors){
//        List<RecordChart> rankss = JSONManager.JsonRecordChartReader();
        return Room.databaseBuilder(context, RecordChartDatabase.class,DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    appExecutors.getDiskIO().execute(() -> {
                        addDelay();
                        RecordChartDatabase database = RecordChartDatabase.getInstance(context,appExecutors);
                        List<RecordChart> ranks = JSONManager.JsonRecordChartReader();
                        insertData(database,ranks);
                        database.setDatabaseCreated();
                    });
            }

        }).build();
    }
    private static void insertData(final RecordChartDatabase database, final List<RecordChart> recordCharts){
        database.runInTransaction(() -> {
            database.rankDao().insertAll(recordCharts);
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
