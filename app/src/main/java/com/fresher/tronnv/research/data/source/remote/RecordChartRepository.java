package com.fresher.tronnv.research.data.source.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.fresher.tronnv.research.data.source.RecordChart;
import com.fresher.tronnv.research.data.source.local.RecordChartDatabase;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Repository modules are responsible for handling data operations. They provide a clean API to the rest of the app.
 * They know where to get the data from and what API calls to make when data is updated.
 * You can consider them as mediators between different data sources (persistent model, web service, cache, etc.).
 */
public class RecordChartRepository {

    private static RecordChartRepository sInstance;
    private final RecordChartDatabase recordChartDatabase;

    private MediatorLiveData<List<RecordChart>> observableRecordChart;

    private RecordChartRepository(final RecordChartDatabase database) {
        this.recordChartDatabase = database;
        observableRecordChart = new MediatorLiveData<>();
        observableRecordChart.addSource(recordChartDatabase.rankDao().getRanks(),
                recordChartEntities -> {
                    if(recordChartDatabase.getDatabaseCreated().getValue() != null){
                        observableRecordChart.postValue(recordChartEntities);
                    }
                });
    }

    public static RecordChartRepository getsInstance(final RecordChartDatabase database){
        if(sInstance == null){
            synchronized (RecordChartRepository.class){
                if(sInstance == null){
                    sInstance = new RecordChartRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<RecordChart> getSong(@NonNull Long id) {
        return recordChartDatabase.rankDao().getRecordChartById(id);
    }
    public LiveData<List<RecordChart>> getAllRank() {
        return observableRecordChart;
    }
}
