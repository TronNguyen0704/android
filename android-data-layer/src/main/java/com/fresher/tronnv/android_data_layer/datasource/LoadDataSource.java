package com.fresher.tronnv.android_data_layer.datasource;

import android.content.Context;

import com.fresher.tronnv.android_data_layer.DataManager;
import com.fresher.tronnv.android_data_layer.json.JSONManager;

public class LoadDataSource {
    private static LoadDataSource instance;
    private DataManager dataManager;
    private LoadDataSource(){
        dataManager = new DataManager();
    }

    private void setContext(Context context){
        dataManager.setContext(context);
    }

    private DataManager getDataManager() {
        return dataManager;
    }
    public void LoadData(Context context){
        setContext(context);
        getDataManager().loadDataFromServer();
    }
    public static LoadDataSource getInstance() {
        if(instance == null){
            instance = new LoadDataSource();
        }
        return instance;
    }
    public boolean isData(){
        return JSONManager.isDataPlaylistExist();
    }
}
