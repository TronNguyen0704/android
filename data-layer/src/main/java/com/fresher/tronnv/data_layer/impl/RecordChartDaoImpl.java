package com.fresher.tronnv.data_layer.impl;

import com.fresher.tronnv.data_layer.DataManager;
import com.fresher.tronnv.data_layer.base.RecordChartDao;
import com.fresher.tronnv.models.RecordChart;

import java.util.List;

public class RecordChartDaoImpl implements RecordChartDao{
    DataManager dataManager ;
    public RecordChartDaoImpl(){
        if(dataManager == null){
            dataManager = new DataManager();

        }
        dataManager.loadDataFromServer();
    }
    @Override
    public List<RecordChart> getRanks() {
        return dataManager.getRecordCharts();
    }

    @Override
    public RecordChart getRecordChartById(Long id) {
        return null;
    }

    @Override
    public void insertRecordChart(RecordChart rank) {

    }

    @Override
    public int updateRecordChart(RecordChart rank) {
        return 0;
    }

    @Override
    public void updateRecordChart(Long id, int rank) {

    }

    @Override
    public int deleteSongById(Long id) {
        return 0;
    }

    @Override
    public void deleteRecordChart() {

    }

    @Override
    public void insertAll(List<RecordChart> ranks) {

    }

    @Override
    public void loadRecordChartData() {
        dataManager.loadRecordChartData();
    }
}
