package com.fresher.tronnv.research.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.fresher.tronnv.research.data.source.RecordChart;

import java.util.List;

@Dao
public interface RecordChartDao {
    @Query("SELECT * FROM ranks")
    LiveData<List<RecordChart>> getRanks();

    @Query("SELECT * FROM ranks WHERE id = :id")
    LiveData<RecordChart> getRecordChartById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecordChart(RecordChart rank);

    @Update
    int updateRecordChart(RecordChart rank);

    @Query("UPDATE ranks SET rank = :rank WHERE id= :id")
    void updateRecordChart(Long id, int rank);

    /*
    Delete a song by id
   * */
    @Query("DELETE FROM ranks WHERE id = :id")
    int deleteSongById(Long id);
    /*
    Delete all song
    * */
    @Query("DELETE FROM ranks")
    void deleteRecordChart();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecordChart> ranks);
}
