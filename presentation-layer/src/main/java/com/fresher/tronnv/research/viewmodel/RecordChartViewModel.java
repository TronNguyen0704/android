package com.fresher.tronnv.research.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;


import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;

import java.util.List;

/**
 * The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations
 */
public class RecordChartViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<RecordChart>> mObservableRecordChart;

    public RecordChartViewModel(Application application) {
        super(application);
        mObservableRecordChart = new MediatorLiveData<>();

        mObservableRecordChart.setValue(null);
        ApplicationPresenter applicationPresenter = new ApplicationPresenterImpl(application.getBaseContext());
        LiveData<List<RecordChart>> ranks = applicationPresenter.getRecordCharts();
        mObservableRecordChart.addSource(ranks, mObservableRecordChart::setValue);
    }
    public LiveData<List<RecordChart>> getRecordChart(){
        return mObservableRecordChart;
    }
}
