package com.fresher.tronnv.research.data.impl;

import android.os.AsyncTask;
import android.os.Handler;

import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.data.base.MusicDao;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;

public class MusicDaoImpl implements MusicDao {
    DataManager dataManager ;
    public MusicDaoImpl (){
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
}
