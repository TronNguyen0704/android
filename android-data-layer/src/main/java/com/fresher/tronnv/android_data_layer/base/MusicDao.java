package com.fresher.tronnv.android_data_layer.base;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.content.Context;


import com.fresher.tronnv.android_models.MusicLyric;

import java.util.List;
@Dao
public interface MusicDao {
    @Query("SELECT * FROM songs")
    LiveData<List<MusicLyric>> getAllMusic();
    @Query("SELECT * FROM songs WHERE name = :filter")
    LiveData<List<MusicLyric>> getMusicByName(String filter);
    @Query("SELECT * FROM songs WHERE id = :id")
    LiveData<MusicLyric> getSongById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSong(MusicLyric song);

    @Update
    int updateSong(MusicLyric song);


    /*
    Delete a song by id
   * */
    @Query("DELETE FROM songs WHERE id = :id")
    int deleteSongById(Long id);
    /*
    Delete all song
    * */
    @Query("DELETE FROM songs")
    void deleteSong();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MusicLyric> musicLyrics);
}
