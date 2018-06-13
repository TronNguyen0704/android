package com.fresher.tronnv.android_data_layer.base;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.content.Context;

import com.fresher.tronnv.android_models.Track;

import java.util.List;

@Dao
public interface TrackDao {
    @Query("SELECT * FROM tracks")
    LiveData<List<Track>> getTracks();

    @Query("SELECT * FROM tracks WHERE id = :id")
    LiveData<Track> getTrackById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrack(Track track);

    @Update
    int updateTrack(Track track);

    /*
    Delete a song by id
   * */
    @Query("DELETE FROM tracks WHERE id = :id")
    int deleteSongById(Long id);
    /*
    Delete all song
    * */
    @Query("DELETE FROM tracks")
    void deleteRecordChart();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Track> tracks);
}