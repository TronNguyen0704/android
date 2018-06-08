package com.fresher.tronnv.domain_layer.base;

import com.fresher.tronnv.models.RecordChart;

import java.util.List;

public interface RecordChartBusiness {
    List<RecordChart> getRecordCharts();

    void loadRecortChartData();
}
