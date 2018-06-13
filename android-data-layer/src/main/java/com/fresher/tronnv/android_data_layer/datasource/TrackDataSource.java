package com.fresher.tronnv.android_data_layer.datasource;

import android.arch.lifecycle.LiveData;


import com.fresher.tronnv.android_models.Track;

import io.reactivex.annotations.NonNull;

public interface TrackDataSource {
    LiveData<Track> getTrack(@NonNull Long id);
}
