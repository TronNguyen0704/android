package com.fresher.tronnv.research.data.impl;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.data.base.MusicDao;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;
import java.util.PriorityQueue;

public class MusicDaoImpl implements MusicDao {
    DataManager dataManager ;
    private Context context;
    public MusicDaoImpl (Context context){
        this.context = context;
        if(dataManager == null){
            dataManager = new DataManager();

        }
        dataManager.setContext(context);
        dataManager.loadDataFromServer();
    }
    @Override
    public List<MusicLyric> getAllMusic(){
        dataManager = new DataManager();
        dataManager.setContext(context);
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
