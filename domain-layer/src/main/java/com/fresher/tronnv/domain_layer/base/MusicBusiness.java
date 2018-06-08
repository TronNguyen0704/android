package com.fresher.tronnv.domain_layer.base;


import com.fresher.tronnv.models.MusicLyric;

import java.util.List;

public interface MusicBusiness {
    List<MusicLyric> getAllMusicData();
    List<MusicLyric> getMusicByName(String filter);
    MusicLyric getSongById(int id);

    void loadMusicData();
}
