package com.fresher.tronnv.android_domain_layer.impl;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.fresher.tronnv.android_data_layer.AppExecutors;
import com.fresher.tronnv.android_data_layer.DataRepository;
import com.fresher.tronnv.android_data_layer.MusicDatabase;
import com.fresher.tronnv.android_data_layer.datasource.LoadDataSource;
import com.fresher.tronnv.android_domain_layer.base.MusicBusiness;
import com.fresher.tronnv.android_models.MusicLyric;

import java.util.List;

public class MusicBusinessImpl implements MusicBusiness {
    private AppExecutors mAppExecutors;
    private Context mContext;
    public MusicBusinessImpl(Context context){
        this.mContext = context;
        mAppExecutors = new AppExecutors();

    }
    private MusicDatabase getMusicDatabase(){
        return MusicDatabase.getInstance(mContext,mAppExecutors);
    }
    private DataRepository getMusicRepository(){
        return DataRepository.getsInstance(getMusicDatabase());
    }
    @Override
    public LiveData<List<MusicLyric>> getAllMusicData() {
        return getMusicRepository().getMusic();
    }

    @Override
    public LiveData<List<MusicLyric>> getMusicByName(String filter) {
        return getMusicRepository().getSong(filter);
    }

    @Override
    public LiveData<MusicLyric> getSongById(int id) {
        return getMusicRepository().getSongById(id);
    }

    @Override
    public void loadMusicData(Context context) {
        LoadDataSource.getInstance().LoadData(context);
    }

    @Override
    public boolean isData() {
        return LoadDataSource.getInstance().isData();
    }

}
