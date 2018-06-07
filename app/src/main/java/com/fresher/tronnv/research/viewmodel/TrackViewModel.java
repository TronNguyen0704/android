package com.fresher.tronnv.research.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.fresher.tronnv.research.MusicApp;
import com.fresher.tronnv.research.data.source.Track;

import java.util.List;

/**
 * The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations
 */
public class TrackViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Track>> observableTrack;

    public TrackViewModel(Application application) {
        super(application);
        observableTrack = new MediatorLiveData<>();

        observableTrack.setValue(null);
        LiveData<List<Track>> tracks = ((MusicApp) application).getTrackRepository().getAllTrack();
        observableTrack.addSource(tracks,observableTrack::setValue);
    }
    public LiveData<List<Track>> getTracks(){
        return  observableTrack;
    }
}
