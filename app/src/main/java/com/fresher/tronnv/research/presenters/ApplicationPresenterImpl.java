package com.fresher.tronnv.research.presenters;

import android.content.Context;
import android.os.AsyncTask;

import com.fresher.tronnv.research.MainUseCases;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.ArrayList;
import java.util.List;

public class ApplicationPresenterImpl implements ApplicationPresenter{
    private MainUseCases mainUseCases;
    public ApplicationPresenterImpl(){
        if(mainUseCases == null)
            this.mainUseCases = new MainUseCases();
    }
    public List<MusicLyric> requestMusic() {
        return  mainUseCases.getAllMusic();
    }

    @Override
    public List<MusicLyric> requestMusic(String filter) {
        return mainUseCases.getMusicByName(filter);
    }

    @Override
    public MusicLyric getSongById(int id) {
        return mainUseCases.getSongById(id);
    }
}
