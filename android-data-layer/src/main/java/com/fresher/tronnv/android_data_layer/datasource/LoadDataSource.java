package com.fresher.tronnv.android_data_layer.datasource;

import android.content.Context;

import com.fresher.tronnv.android_data_layer.DataManager;
import com.fresher.tronnv.android_data_layer.json.JSONManager;

public class LoadDataSource {
    private static LoadDataSource sInstance;
    private DataManager mDataManager;
    private LoadDataSource(){
        mDataManager = new DataManager();
    }

    private void setContext(Context context){
        mDataManager.setContext(context);
    }

    private DataManager getDataManager() {
        return mDataManager;
    }
    public void LoadData(Context context){
        setContext(context);
        getDataManager().loadDataFromServer();
    }
    public static LoadDataSource getInstance() {
        if(sInstance == null){
            sInstance = new LoadDataSource();
        }
        return sInstance;
    }
    public boolean isData(){
        return JSONManager.isDataPlaylistExist();
    }
}
