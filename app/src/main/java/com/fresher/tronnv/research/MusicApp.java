package com.fresher.tronnv.research;

import android.app.Application;

import com.fresher.tronnv.research.data.AppExecutors;
import com.fresher.tronnv.research.data.source.local.MusicDatabase;
import com.fresher.tronnv.research.data.source.remote.DataRepository;

public class MusicApp extends Application {
    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        appExecutors = new AppExecutors();
    }

    public MusicDatabase getRecordChartDatabase(){
        return MusicDatabase.getInstance(this,appExecutors);
    }
    public DataRepository getRecordChartRepository(){
        return DataRepository.getsInstance(getRecordChartDatabase());
    }
    public MusicDatabase getTrackDatabase(){
        return MusicDatabase.getInstance(this,appExecutors);
    }
    public DataRepository getTrackRepository(){
        return DataRepository.getsInstance(getTrackDatabase());
    }
}
