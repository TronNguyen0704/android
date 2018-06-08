package com.fresher.tronnv.domain_layer.impl;

import com.fresher.tronnv.data_layer.base.RecordChartDao;
import com.fresher.tronnv.data_layer.impl.RecordChartDaoImpl;
import com.fresher.tronnv.domain_layer.base.RecordChartBusiness;
import com.fresher.tronnv.models.RecordChart;

import java.util.List;

public class RecordChartBusinessImpl implements RecordChartBusiness{
    private RecordChartDao recordChartDao;
    public RecordChartBusinessImpl(){
        if(recordChartDao == null){
             recordChartDao = new RecordChartDaoImpl();
        }
    }

    @Override
    public List<RecordChart> getRecordCharts() {
        return recordChartDao.getRanks();
    }

    @Override
    public void loadRecortChartData() {
        recordChartDao.loadRecordChartData();
    }
}
