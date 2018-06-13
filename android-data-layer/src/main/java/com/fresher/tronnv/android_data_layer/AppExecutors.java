package com.fresher.tronnv.android_data_layer;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.annotations.NonNull;

public class AppExecutors {
    private final Executor diskIO;
    private final Executor netWorkIO;
    private final Executor mainThread;

    private AppExecutors(Executor diskIO, Executor netWorkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.netWorkIO = netWorkIO;
        this.mainThread = mainThread;
    }
    public AppExecutors(){
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                new MainThreadExecutor());
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetWorkIO() {
        return netWorkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command){
            mainThreadHandler.post(command);
        }
    }
}
