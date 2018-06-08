package com.fresher.tronnv.data_layer.impl;

import com.fresher.tronnv.data_layer.DataManager;
import com.fresher.tronnv.data_layer.base.TrackDao;
import com.fresher.tronnv.models.Track;

import java.util.List;

public class TrackDaoImpl implements TrackDao{

    DataManager dataManager ;
    public TrackDaoImpl(){
        if(dataManager == null){
            dataManager = new DataManager();

        }
        dataManager.loadDataFromServer();
    }
    @Override
    public List<Track> getTracks() {
        return dataManager.getTracks();
    }

    @Override
    public Track getTrackById(Long id) {
        return null;
    }

    @Override
    public void insertTrack(Track track) {

    }

    @Override
    public int updateTrack(Track track) {
        return 0;
    }

    @Override
    public int deleteSongById(Long id) {
        return 0;
    }

    @Override
    public void deleteRecordChart() {

    }

    @Override
    public void insertAll(List<Track> tracks) {

    }

    @Override
    public void loadTrackData() {
        dataManager.loadTrackData();
    }
}
