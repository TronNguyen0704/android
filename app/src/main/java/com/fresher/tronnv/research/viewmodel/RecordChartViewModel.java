package com.fresher.tronnv.research.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.fresher.tronnv.research.MusicApp;
import com.fresher.tronnv.research.data.source.RecordChart;

import java.util.List;

public class RecordChartViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<RecordChart>> observableRecordChart;

    public RecordChartViewModel(Application application) {
        super(application);
        observableRecordChart = new MediatorLiveData<>();

        observableRecordChart.setValue(null);
        LiveData<List<RecordChart>> ranks = ((MusicApp) application).getRepository().getAllRank();
        observableRecordChart.addSource(ranks,observableRecordChart::setValue);
    }
    public LiveData<List<RecordChart>> getRecordChart(){
        return  observableRecordChart;
    }
}
