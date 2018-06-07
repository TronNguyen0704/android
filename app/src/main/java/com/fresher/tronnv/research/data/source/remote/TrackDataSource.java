package com.fresher.tronnv.research.data.source.remote;

import android.arch.lifecycle.LiveData;
import com.fresher.tronnv.research.data.source.Track;

import io.reactivex.annotations.NonNull;

public interface TrackDataSource {
    LiveData<Track> getTrack(@NonNull Long id);
}
