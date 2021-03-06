package com.fresher.tronnv.android_domain_layer.impl;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.fresher.tronnv.android_data_layer.AppExecutors;
import com.fresher.tronnv.android_data_layer.DataRepository;
import com.fresher.tronnv.android_data_layer.MusicDatabase;
import com.fresher.tronnv.android_data_layer.datasource.LoadDataSource;
import com.fresher.tronnv.android_domain_layer.base.TrackBusiness;
import com.fresher.tronnv.android_models.Track;

import java.util.List;

public class TrackBusinessImpl implements TrackBusiness {
    private AppExecutors mAppExecutors;
    private Context mContext;
    public TrackBusinessImpl(Context context){
        this.mContext = context;
        mAppExecutors = new AppExecutors();
    }
    public MusicDatabase getMusicDatabase(){
        return MusicDatabase.getInstance(mContext,mAppExecutors);
    }
    public DataRepository getMusicRepository(){
        return DataRepository.getsInstance(getMusicDatabase());
    }
    @Override
    public LiveData<List<Track>> getTracks() {
        return getMusicRepository().getAllTrack();
    }

    @Override
    public void loadTrackData(Context context) {
        LoadDataSource.getInstance().LoadData(context);
    }

    @Override
    public boolean isData() {
        return LoadDataSource.getInstance().isData();
    }

}
