package com.fresher.tronnv.research.domain.impl;

import android.content.Context;

import com.fresher.tronnv.research.data.base.MusicDao;
import com.fresher.tronnv.research.data.impl.MusicDaoImpl;
import com.fresher.tronnv.research.domain.base.MusicBusiness;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;

public class MusicBusinessImpl implements MusicBusiness{
    private MusicDao dao;
    public MusicBusinessImpl(Context context){
        if(dao == null)
            dao = new MusicDaoImpl(context);
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
}