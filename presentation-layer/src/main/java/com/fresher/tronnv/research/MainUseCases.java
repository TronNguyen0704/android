package com.fresher.tronnv.research;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;


import com.fresher.tronnv.android_domain_layer.base.MusicBusiness;
import com.fresher.tronnv.android_domain_layer.base.RecordChartBusiness;
import com.fresher.tronnv.android_domain_layer.base.TrackBusiness;
import com.fresher.tronnv.android_domain_layer.impl.MusicBusinessImpl;
import com.fresher.tronnv.android_domain_layer.impl.RecordChartBusinessImpl;
import com.fresher.tronnv.android_domain_layer.impl.TrackBusinessImpl;
import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;

import java.util.List;

public class MainUseCases {
    private MusicBusiness musicBusiness;
    private RecordChartBusiness recordChartBusiness;
    private TrackBusiness trackBusiness;
    private Context context;
    public MainUseCases(Context context){
        this.context = context;
        if(musicBusiness == null)
            this.musicBusiness = new MusicBusinessImpl(context);
        if(recordChartBusiness == null)
            this.recordChartBusiness = new RecordChartBusinessImpl(context);
        if(trackBusiness == null)
            this.trackBusiness = new TrackBusinessImpl(context);
    }
    public void loadMusicData(){
        musicBusiness.loadMusicData(context);
    }
    public void loadTrackData(){
        trackBusiness.loadTrackData(context);
    }
    public void loadRecortChartData(){
        recordChartBusiness.loadRecortChartData(context);
    }
    public LiveData<List<MusicLyric>> getAllMusic() {
        return musicBusiness.getAllMusicData();
    }
    public LiveData<List<MusicLyric>> getMusicByName(String filter){
        return musicBusiness.getMusicByName(filter);
    }
    public LiveData<MusicLyric> getSongById(int id){
        return musicBusiness.getSongById(id);
    }
    public LiveData<List<RecordChart>> getRecordChart(){
        return recordChartBusiness.getRecordCharts();
    }
    public LiveData<List<Track>> getTracks(){
        return trackBusiness.getTracks();
    }

    public boolean isData(){
        return isRecordChartData() && isMusicData() && isTrackData();
    }
    public boolean isRecordChartData() {
        return recordChartBusiness.isData();
    }
    public boolean isMusicData() {
        return musicBusiness.isData();
    }
    public boolean isTrackData() {
        return trackBusiness.isData();
    }
}
