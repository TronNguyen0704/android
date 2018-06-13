package com.fresher.tronnv.android_domain_layer.base;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.fresher.tronnv.android_models.Track;

import java.util.List;

public interface TrackBusiness {
   LiveData<List<Track>> getTracks();

    void loadTrackData(Context context);

    boolean isData();
}
