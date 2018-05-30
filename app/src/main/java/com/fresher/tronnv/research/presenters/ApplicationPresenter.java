package com.fresher.tronnv.research.presenters;

import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.presenters.base.Presenter;

import java.util.List;

public interface ApplicationPresenter extends Presenter{
    List<MusicLyric> requestMusic();
    List<MusicLyric> requestMusic(String filter);
    MusicLyric getSongById(int id);
}
