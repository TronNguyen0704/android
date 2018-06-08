package com.fresher.tronnv.data_layer.base;



import com.fresher.tronnv.models.Track;

import java.util.List;


public interface TrackDao {
    List<Track> getTracks();

    Track getTrackById(Long id);

    void insertTrack(Track track);

    int updateTrack(Track track);

    /*
    Delete a song by id
   * */
    int deleteSongById(Long id);
    /*
    Delete all song
    * */
    void deleteRecordChart();

    void insertAll(List<Track> tracks);

    void loadTrackData();
}
