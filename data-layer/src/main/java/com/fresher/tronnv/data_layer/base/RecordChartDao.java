package com.fresher.tronnv.data_layer.base;



import com.fresher.tronnv.models.RecordChart;

import java.util.List;

public interface RecordChartDao {

    List<RecordChart> getRanks();


   RecordChart getRecordChartById(Long id);


    void insertRecordChart(RecordChart rank);


    int updateRecordChart(RecordChart rank);


    void updateRecordChart(Long id, int rank);

    /*
    Delete a song by id
   * */

    int deleteSongById(Long id);
    /*
    Delete all song
    * */

    void deleteRecordChart();


    void insertAll(List<RecordChart> ranks);

    void loadRecordChartData();
}
