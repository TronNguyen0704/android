package com.fresher.tronnv.android_domain_layer.base;




import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.fresher.tronnv.android_models.MusicLyric;

import java.util.List;

public interface MusicBusiness {
    LiveData<List<MusicLyric>> getAllMusicData();
    LiveData<List<MusicLyric>> getMusicByName(String filter);
    LiveData<MusicLyric> getSongById(int id);

    void loadMusicData(Context context);

    boolean isData();
}
