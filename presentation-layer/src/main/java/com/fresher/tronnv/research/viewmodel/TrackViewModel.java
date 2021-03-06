package com.fresher.tronnv.research.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;


import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;

import java.util.List;

/**
 * The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations
 */
public class TrackViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Track>> mObservableTrack;

    public TrackViewModel(Application application) {
        super(application);
        mObservableTrack = new MediatorLiveData<>();

        mObservableTrack.setValue(null);
        ApplicationPresenter applicationPresenter = new ApplicationPresenterImpl(application.getBaseContext());
        LiveData<List<Track>> tracks = applicationPresenter.getTracks();
        mObservableTrack.addSource(tracks, mObservableTrack::setValue);
    }
    public LiveData<List<Track>> getTracks(){
        return mObservableTrack;
    }
}
