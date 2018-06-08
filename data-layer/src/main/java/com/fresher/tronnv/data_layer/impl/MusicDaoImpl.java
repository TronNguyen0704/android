package com.fresher.tronnv.data_layer.impl;


import com.fresher.tronnv.data_layer.DataManager;
import com.fresher.tronnv.data_layer.base.MusicDao;
import com.fresher.tronnv.models.MusicLyric;

import java.util.List;

public class MusicDaoImpl implements MusicDao {
    DataManager dataManager ;
    public MusicDaoImpl(){
        if(dataManager == null){
            dataManager = new DataManager();

        }
        dataManager.loadDataFromServer();
    }
    @Override
    public List<MusicLyric> getAllMusic(){
        dataManager = new DataManager();
        dataManager.loadDataFromServer();
        return dataManager.getMusicLyrics();
    }

    @Override
    public List<MusicLyric> getMusicByName(String filter) {
        return dataManager.getMusicByName(filter);
    }

    @Override
    public MusicLyric getSongById(int id) {
        return dataManager.getSongById(id);
    }

    @Override
    public void loadMusicData() {
        dataManager.loadMusicData();
    }
}
