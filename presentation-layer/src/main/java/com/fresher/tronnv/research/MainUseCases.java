package com.fresher.tronnv.research;

import android.arch.lifecycle.LiveData;
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
    private MusicBusiness mMusicBusiness;
    private RecordChartBusiness mRecordChartBusiness;
    private TrackBusiness mTrackBusiness;
    private Context mContext;
    public MainUseCases(Context context){
        this.mContext = context;
        if(mMusicBusiness == null)
            this.mMusicBusiness = new MusicBusinessImpl(context);
        if(mRecordChartBusiness == null)
            this.mRecordChartBusiness = new RecordChartBusinessImpl(context);
        if(mTrackBusiness == null)
            this.mTrackBusiness = new TrackBusinessImpl(context);
    }
    public void loadMusicData(){
        mMusicBusiness.loadMusicData(mContext);
    }
    public void loadTrackData(){
        mTrackBusiness.loadTrackData(mContext);
    }
    public void loadRecortChartData(){
        mRecordChartBusiness.loadRecortChartData(mContext);
    }
    public LiveData<List<MusicLyric>> getAllMusic() {
        return mMusicBusiness.getAllMusicData();
    }
    public LiveData<List<MusicLyric>> getMusicByName(String filter){
        return mMusicBusiness.getMusicByName(filter);
    }
    public LiveData<MusicLyric> getSongById(int id){
        return mMusicBusiness.getSongById(id);
    }
    public LiveData<List<RecordChart>> getRecordChart(){
        return mRecordChartBusiness.getRecordCharts();
    }
    public LiveData<List<Track>> getTracks(){
        return mTrackBusiness.getTracks();
    }

    public boolean isData(){
        return isRecordChartData() && isMusicData() && isTrackData();
    }
    public boolean isRecordChartData() {
        return mRecordChartBusiness.isData();
    }
    public boolean isMusicData() {
        return mMusicBusiness.isData();
    }
    public boolean isTrackData() {
        return mTrackBusiness.isData();
    }
}
