package com.fresher.tronnv.research.presenters;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.MainUseCases;

import java.util.List;

public class ApplicationPresenterImpl implements ApplicationPresenter{
    private MainUseCases mainUseCases;
    public ApplicationPresenterImpl(Context context){
        if(mainUseCases == null)
            this.mainUseCases = new MainUseCases(context);
    }
    public LiveData<List<MusicLyric>> requestMusic() {
        return  mainUseCases.getAllMusic();
    }

    @Override
    public LiveData<List<MusicLyric>> requestMusic(String filter) {
        return mainUseCases.getMusicByName(filter);
    }

    @Override
    public LiveData<MusicLyric> getSongById(int id) {
        return mainUseCases.getSongById(id);
    }

    @Override
    public LiveData<List<Track>> getTracks() {
        return mainUseCases.getTracks();
    }

    @Override
    public LiveData<List<RecordChart>> getRecordCharts() {
        return mainUseCases.getRecordChart();
    }

    @Override
    public boolean isData() {
        return mainUseCases.isData();
    }

    @Override
    public void loadTrackData() {
        mainUseCases.loadTrackData();
    }

    @Override
    public void loadRecordChartData() {
        mainUseCases.loadRecortChartData();
    }

    @Override
    public void loadMusicData() {
        mainUseCases.loadMusicData();
    }
}
