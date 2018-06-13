package com.fresher.tronnv.android_domain_layer.base;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.fresher.tronnv.android_models.RecordChart;

import java.util.List;

public interface RecordChartBusiness {
    LiveData<List<RecordChart>> getRecordCharts();

    void loadRecortChartData(Context context);

    boolean isData();
}
