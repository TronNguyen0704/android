package com.fresher.tronnv.domain_layer.impl;

import com.fresher.tronnv.data_layer.base.TrackDao;
import com.fresher.tronnv.data_layer.impl.TrackDaoImpl;
import com.fresher.tronnv.domain_layer.base.TrackBusiness;
import com.fresher.tronnv.models.Track;

import java.util.List;

public class TrackBusinessImpl implements TrackBusiness{
    private TrackDao trackDao;

    public TrackBusinessImpl(){
        if(trackDao == null){
            trackDao = new TrackDaoImpl();
        }
    }
    @Override
    public List<Track> getTracks() {
        return trackDao.getTracks();
    }

    @Override
    public void loadTrackData() {
        trackDao.loadTrackData();
    }
}
