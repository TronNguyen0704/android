package com.fresher.tronnv.research;

import android.app.Application;

import com.fresher.tronnv.research.data.AppExecutors;
import com.fresher.tronnv.research.data.source.local.RecordChartDatabase;
import com.fresher.tronnv.research.data.source.remote.RecordChartRepository;

public class MusicApp extends Application {
    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        appExecutors = new AppExecutors();
    }

    public RecordChartDatabase getDatabase(){
        return RecordChartDatabase.getInstance(this,appExecutors);
    }
    public RecordChartRepository getRepository(){
        return RecordChartRepository.getsInstance(getDatabase());
    }
}
