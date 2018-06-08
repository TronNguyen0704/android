package com.fresher.tronnv.research.presenters;

import com.fresher.tronnv.models.MusicLyric;
import com.fresher.tronnv.models.RecordChart;
import com.fresher.tronnv.models.Track;
import com.fresher.tronnv.research.MainUseCases;

import java.util.List;

public class ApplicationPresenterImpl implements ApplicationPresenter{
    private MainUseCases mainUseCases;
    public ApplicationPresenterImpl(){
        if(mainUseCases == null)
            this.mainUseCases = new MainUseCases();
    }
    public List<MusicLyric> requestMusic() {
        return  mainUseCases.getAllMusic();
    }

    @Override
    public List<MusicLyric> requestMusic(String filter) {
        return mainUseCases.getMusicByName(filter);
    }

    @Override
    public MusicLyric getSongById(int id) {
        return mainUseCases.getSongById(id);
    }

    @Override
    public List<Track> getTracks() {
        return mainUseCases.getTracks();
    }

    @Override
    public List<RecordChart> getRecordCharts() {
        return mainUseCases.getRecordChart();
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
