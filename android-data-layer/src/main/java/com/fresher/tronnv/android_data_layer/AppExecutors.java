package com.fresher.tronnv.android_data_layer;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.annotations.NonNull;

public class AppExecutors {
    private final Executor mDiskIO;
    private final Executor mNetWorkIO;
    private final Executor mMainThread;

    private AppExecutors(Executor diskIO, Executor netWorkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mNetWorkIO = netWorkIO;
        this.mMainThread = mainThread;
    }
    public AppExecutors(){
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                new MainThreadExecutor());
    }

    public Executor getDiskIO() {
        return mDiskIO;
    }

    public Executor getNetWorkIO() {
        return mNetWorkIO;
    }

    public Executor getMainThread() {
        return mMainThread;
    }
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command){
            mainThreadHandler.post(command);
        }
    }
}
