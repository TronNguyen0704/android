package com.fresher.tronnv.research;

import com.fresher.tronnv.research.domain.base.MusicBusiness;
import com.fresher.tronnv.research.domain.impl.MusicBusinessImpl;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;

public class MainUseCases {
    private MusicBusiness musicBusiness;
    public MainUseCases(){
        if(musicBusiness == null)
            this.musicBusiness = new MusicBusinessImpl();
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
