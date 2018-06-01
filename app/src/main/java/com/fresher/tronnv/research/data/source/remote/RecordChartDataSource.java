package com.fresher.tronnv.research.data.source.remote;

import android.arch.lifecycle.LiveData;

import com.fresher.tronnv.research.data.source.RecordChart;

import io.reactivex.annotations.NonNull;

public interface RecordChartDataSource {
    LiveData<RecordChart> getSong(@NonNull Long id);
    void saveRank(@NonNull RecordChart rank);
    void deleteAllSong();
    void deleteRank(@NonNull Long id);
}
