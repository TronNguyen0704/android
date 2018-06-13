package com.fresher.tronnv.android_data_layer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;


import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;

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
    private MediatorLiveData<List<MusicLyric>> observableMusic;
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
        observableMusic = new MediatorLiveData<>();
        observableMusic.addSource(musicDatabase.musicDao().getAllMusic(),
                musicEntities -> {
                    if(musicDatabase.getDatabaseCreated().getValue() != null){
                        observableMusic.postValue(musicEntities);
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
    public LiveData<MusicLyric> getSongById(@NonNull Integer id) {
        return musicDatabase.musicDao().getSongById(id);
    }
    public LiveData<List<MusicLyric>> getSong(@NonNull String filter) {
        return musicDatabase.musicDao().getMusicByName(filter);
    }
    public LiveData<List<RecordChart>> getAllRank() {
        return observableRecordChart;
    }
    public LiveData<List<Track>> getAllTrack() {
        return observableTrack;
    }
    public LiveData<List<MusicLyric>> getMusic() {
        return observableMusic;
    }
}
