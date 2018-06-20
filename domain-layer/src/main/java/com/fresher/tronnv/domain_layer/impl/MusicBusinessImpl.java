package com.fresher.tronnv.domain_layer.impl;


import com.fresher.tronnv.data_layer.base.MusicDao;
import com.fresher.tronnv.models.MusicLyric;
import com.fresher.tronnv.domain_layer.base.MusicBusiness;

import java.util.List;

public class MusicBusinessImpl implements MusicBusiness {
    private MusicDao dao;
    public MusicBusinessImpl(){
        if(dao == null)
            dao = new MusicDaoImpl();
    }

    @Override
    public List<MusicLyric> getAllMusicData() {
        return dao.getAllMusic();
    }

    @Override
    public List<MusicLyric> getMusicByName(String filter) {
        return dao.getMusicByName(filter);
    }

    @Override
    public MusicLyric getSongById(int id) {
        return dao.getSongById(id);
    }

    @Override
    public void loadMusicData() {
        dao.loadMusicData();
    }
}
