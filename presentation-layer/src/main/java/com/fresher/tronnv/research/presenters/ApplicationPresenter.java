package com.fresher.tronnv.research.presenters;


import android.arch.lifecycle.LiveData;

import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.presenters.base.Presenter;

import java.util.List;

public interface ApplicationPresenter extends Presenter{
    LiveData<List<MusicLyric>> requestMusic();
    LiveData<List<MusicLyric>> requestMusic(String filter);
    LiveData<MusicLyric> getSongById(int id);
    LiveData<List<Track>> getTracks();
    LiveData<List<RecordChart>> getRecordCharts();
    boolean isData();
    void loadTrackData();
    void loadRecordChartData();
    void loadMusicData();
}
