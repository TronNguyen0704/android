package com.fresher.tronnv.research.data.source.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.fresher.tronnv.research.data.source.RecordChart;
import com.fresher.tronnv.research.data.source.Track;
import com.fresher.tronnv.research.data.source.local.MusicDatabase;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Repository modules are responsible for handling data operations. They provide a clean API to the rest of the app.
 * They know where to get the data from and what API calls to make when data is updated.
 * You can consider them as mediators between different data sources (persistent model, web service, cache, etc.).
 */
public class DataRepository {

    private static DataRepository sInstance;
    private final MusicDatabase musicDatabase;

    private MediatorLiveData<List<RecordChart>> observableRecordChart;
    private MediatorLiveData<List<Track>> observableTrack;
    private DataRepository(final MusicDatabase database) {
        this.musicDatabase = database;
        observableRecordChart = new MediatorLiveData<>();
        observableRecordChart.addSource(musicDatabase.rankDao().getRanks(),
                recordChartEntities -> {
                    if(musicDatabase.getDatabaseCreated().getValue() != null){
                        observableRecordChart.postValue(recordChartEntities);
                    }
                });

        observableTrack = new MediatorLiveData<>();
        observableTrack.addSource(musicDatabase.trackDao().getTracks(),
                trackEntities -> {
                    if(musicDatabase.getDatabaseCreated().getValue() != null){
                        observableTrack.postValue(trackEntities);
                    }
                });
    }

    public static DataRepository getsInstance(final MusicDatabase database){
        if(sInstance == null){
            synchronized (DataRepository.class){
                if(sInstance == null){
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<RecordChart> getSong(@NonNull Long id) {
        return musicDatabase.rankDao().getRecordChartById(id);
    }
    public LiveData<List<RecordChart>> getAllRank() {
        return observableRecordChart;
    }
    public LiveData<List<Track>> getAllTrack() {
        return observableTrack;
    }
}
