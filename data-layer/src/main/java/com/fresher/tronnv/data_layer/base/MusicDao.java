package com.fresher.tronnv.data_layer.base;

import java.util.List;

import com.fresher.tronnv.models.MusicLyric;

public interface MusicDao {
    List<MusicLyric> getAllMusic();
    List<MusicLyric> getMusicByName(String filter);
    MusicLyric getSongById(int id);

    void loadMusicData();
}
