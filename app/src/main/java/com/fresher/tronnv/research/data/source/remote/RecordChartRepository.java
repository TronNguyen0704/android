package com.fresher.tronnv.research.data.source.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.fresher.tronnv.research.data.source.RecordChart;
import com.fresher.tronnv.research.data.source.local.RecordChartDatabase;

import java.util.List;

import io.reactivex.annotations.NonNull;

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
