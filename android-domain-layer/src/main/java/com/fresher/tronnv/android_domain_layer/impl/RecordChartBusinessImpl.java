package com.fresher.tronnv.android_domain_layer.impl;



import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.fresher.tronnv.android_data_layer.AppExecutors;
import com.fresher.tronnv.android_data_layer.DataRepository;
import com.fresher.tronnv.android_data_layer.MusicDatabase;
import com.fresher.tronnv.android_data_layer.datasource.LoadDataSource;
import com.fresher.tronnv.android_domain_layer.base.RecordChartBusiness;
import com.fresher.tronnv.android_models.RecordChart;

import java.util.List;

public class RecordChartBusinessImpl implements RecordChartBusiness {
    private AppExecutors mAppExecutors;
    private Context mContext;
    public RecordChartBusinessImpl(Context context){
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
    public LiveData<List<RecordChart>> getRecordCharts() {
        return getMusicRepository().getAllRank();
    }

    @Override
    public void loadRecortChartData(Context context) {
        LoadDataSource.getInstance().LoadData(context);
    }

    @Override
    public boolean isData() {
        return LoadDataSource.getInstance().isData();
    }
}
