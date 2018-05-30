package com.fresher.tronnv.research.data.base;

import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;

public interface MusicDao {
    List<MusicLyric> getAllMusic();
    List<MusicLyric> getMusicByName(String filter);
    MusicLyric getSongById(int id);
}
