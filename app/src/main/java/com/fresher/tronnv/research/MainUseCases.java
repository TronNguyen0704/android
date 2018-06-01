package com.fresher.tronnv.research;

import android.content.Context;

import com.fresher.tronnv.research.domain.base.MusicBusiness;
import com.fresher.tronnv.research.domain.impl.MusicBusinessImpl;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;

public class MainUseCases {
    private MusicBusiness musicBusiness;
    public MainUseCases(Context context){
        if(musicBusiness == null)
            this.musicBusiness = new MusicBusinessImpl(context);
    }
    public List<MusicLyric> getAllMusic() {
        return musicBusiness.getAllMusicData();
    }
    public List<MusicLyric> getMusicByName(String filter){
        return musicBusiness.getMusicByName(filter);
    }
    public MusicLyric getSongById(int id){
        return musicBusiness.getSongById(id);
    }
}
