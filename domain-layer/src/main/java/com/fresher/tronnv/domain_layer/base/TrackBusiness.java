package com.fresher.tronnv.domain_layer.base;

import com.fresher.tronnv.models.Track;

import java.util.List;

public interface TrackBusiness {
   List<Track> getTracks();

    void loadTrackData();
}
