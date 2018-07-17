package com.fresher.tronnv.research.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;

import java.util.List;

/**
 * The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations
 */
public class MusicViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<MusicLyric>> mObservableMusic;

    public MusicViewModel(Application application) {
        super(application);
        mObservableMusic = new MediatorLiveData<>();

        mObservableMusic.setValue(null);
        ApplicationPresenter applicationPresenter = new ApplicationPresenterImpl(application.getBaseContext());
        LiveData<List<MusicLyric>> musics = applicationPresenter.requestMusic();
        mObservableMusic.addSource(musics, mObservableMusic::setValue);
    }
    public LiveData<List<MusicLyric>> getMusics(){
        return mObservableMusic;
    }
}
