package com.fresher.tronnv.android_data_layer.datasource;

import android.arch.lifecycle.LiveData;


import com.fresher.tronnv.android_models.RecordChart;

import io.reactivex.annotations.NonNull;

public interface RecordChartDataSource {
    LiveData<RecordChart> getSong(@NonNull Long id);
    void saveRank(@NonNull RecordChart rank);
    void deleteAllSong();
    void deleteRank(@NonNull Long id);
}
