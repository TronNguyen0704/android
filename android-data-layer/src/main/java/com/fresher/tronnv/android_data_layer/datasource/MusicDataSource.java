package com.fresher.tronnv.android_data_layer.datasource;

import android.arch.lifecycle.LiveData;

import com.fresher.tronnv.android_models.MusicLyric;

import io.reactivex.annotations.NonNull;

public interface MusicDataSource {
    LiveData<MusicLyric> getMusics(@NonNull Long id);
}
