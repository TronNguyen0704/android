package com.fresher.tronnv.research;

import android.content.Context;


import com.fresher.tronnv.domain_layer.base.MusicBusiness;
import com.fresher.tronnv.domain_layer.base.RecordChartBusiness;
import com.fresher.tronnv.domain_layer.base.TrackBusiness;
import com.fresher.tronnv.domain_layer.impl.MusicBusinessImpl;
import com.fresher.tronnv.domain_layer.impl.RecordChartBusinessImpl;
import com.fresher.tronnv.domain_layer.impl.TrackBusinessImpl;
import com.fresher.tronnv.models.MusicLyric;
import com.fresher.tronnv.models.RecordChart;
import com.fresher.tronnv.models.Track;

import java.util.List;

public class MainUseCases {
    private MusicBusiness musicBusiness;
    private RecordChartBusiness recordChartBusiness;
    private TrackBusiness trackBusiness;
    public MainUseCases(){
        if(musicBusiness == null)
            this.musicBusiness = new MusicBusinessImpl();
        if(recordChartBusiness == null)
            this.recordChartBusiness = new RecordChartBusinessImpl();
        if(trackBusiness == null)
            this.trackBusiness = new TrackBusinessImpl();
    }
    public void loadMusicData(){
        musicBusiness.loadMusicData();
    }
    public void loadTrackData(){
        trackBusiness.loadTrackData();
    }
    public void loadRecortChartData(){
        recordChartBusiness.loadRecortChartData();
    }
    public List<MusicLyric> getAllMusic() {
        return musicBusiness.getAllMusicData();
    }
    public List<MusicLyric> getMusicByName(String filter){
        return musicBusiness.getMusicByName(filter);
    }
    public MusicLyric getSongById(int id){
        return musicBusiness.getSongById(id);
    }
    public List<RecordChart> getRecordChart(){
        return recordChartBusiness.getRecordCharts();
    }
    public List<Track> getTracks(){
        return trackBusiness.getTracks();
    }
}
