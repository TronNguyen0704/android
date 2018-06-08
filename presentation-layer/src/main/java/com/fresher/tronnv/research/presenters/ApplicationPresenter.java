package com.fresher.tronnv.research.presenters;

import com.fresher.tronnv.models.MusicLyric;
import com.fresher.tronnv.models.RecordChart;
import com.fresher.tronnv.models.Track;
import com.fresher.tronnv.research.presenters.base.Presenter;

import java.util.List;

public interface ApplicationPresenter extends Presenter{
    List<MusicLyric> requestMusic();
    List<MusicLyric> requestMusic(String filter);
    MusicLyric getSongById(int id);
    List<Track> getTracks();
    List<RecordChart> getRecordCharts();

    void loadTrackData();
    void loadRecordChartData();
    void loadMusicData();
}
