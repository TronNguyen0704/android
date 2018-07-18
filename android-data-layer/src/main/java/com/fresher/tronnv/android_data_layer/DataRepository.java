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
    private final MusicDatabase mMusicDatabase;

    private MediatorLiveData<List<RecordChart>> mObservableRecordChart;
    private MediatorLiveData<List<Track>> mObservableTrack;
    private MediatorLiveData<List<MusicLyric>> mObservableMusic;
    private DataRepository(final MusicDatabase database) {
        this.mMusicDatabase = database;
        mObservableRecordChart = new MediatorLiveData<>();
        mObservableRecordChart.addSource(mMusicDatabase.rankDao().getRanks(),
                recordChartEntities -> {
                    if(mMusicDatabase.getDatabaseCreated().getValue() != null){
                        mObservableRecordChart.postValue(recordChartEntities);
                    }
                });

        mObservableTrack = new MediatorLiveData<>();
        mObservableTrack.addSource(mMusicDatabase.trackDao().getTracks(),
                trackEntities -> {
                    if(mMusicDatabase.getDatabaseCreated().getValue() != null){
                        mObservableTrack.postValue(trackEntities);
                    }
                });
        mObservableMusic = new MediatorLiveData<>();
        mObservableMusic.addSource(mMusicDatabase.musicDao().getAllMusic(),
                musicEntities -> {
                    if(mMusicDatabase.getDatabaseCreated().getValue() != null){
                        mObservableMusic.postValue(musicEntities);
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
        return mMusicDatabase.rankDao().getRecordChartById(id);
    }
    public LiveData<MusicLyric> getSongById(@NonNull Integer id) {
        return mMusicDatabase.musicDao().getSongById(id);
    }
    public LiveData<List<MusicLyric>> getSong(@NonNull String filter) {
        return mMusicDatabase.musicDao().getMusicByName(filter);
    }
    public LiveData<List<RecordChart>> getAllRank() {
        return mObservableRecordChart;
    }
    public LiveData<List<Track>> getAllTrack() {
        return mObservableTrack;
    }
    public LiveData<List<MusicLyric>> getMusic() {
        return mObservableMusic;
    }
}
