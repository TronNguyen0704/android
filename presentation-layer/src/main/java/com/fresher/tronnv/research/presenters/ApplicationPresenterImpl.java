package com.fresher.tronnv.research.presenters;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.MainUseCases;

import java.util.List;

public class ApplicationPresenterImpl implements ApplicationPresenter{
    private MainUseCases mMainUseCases;
    public ApplicationPresenterImpl(Context context){
        if(mMainUseCases == null)
            this.mMainUseCases = new MainUseCases(context);
    }
    public LiveData<List<MusicLyric>> requestMusic() {
        return  mMainUseCases.getAllMusic();
    }

    @Override
    public LiveData<List<MusicLyric>> requestMusic(String filter) {
        return mMainUseCases.getMusicByName(filter);
    }

    @Override
    public LiveData<MusicLyric> getSongById(int id) {
        return mMainUseCases.getSongById(id);
    }

    @Override
    public LiveData<List<Track>> getTracks() {
        return mMainUseCases.getTracks();
    }

    @Override
    public LiveData<List<RecordChart>> getRecordCharts() {
        return mMainUseCases.getRecordChart();
    }

    @Override
    public boolean isData() {
        return mMainUseCases.isData();
    }

    @Override
    public void loadTrackData() {
        mMainUseCases.loadTrackData();
    }

    @Override
    public void loadRecordChartData() {
        mMainUseCases.loadRecortChartData();
    }

    @Override
    public void loadMusicData() {
        mMainUseCases.loadMusicData();
    }
}
