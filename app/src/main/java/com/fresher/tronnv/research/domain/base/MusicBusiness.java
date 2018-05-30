package com.fresher.tronnv.research.domain.base;

import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;

public interface MusicBusiness {
    List<MusicLyric> getAllMusicData();
    List<MusicLyric> getMusicByName(String filter);
    MusicLyric getSongById(int id);
}
