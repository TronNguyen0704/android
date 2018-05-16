package com.fresher.tronnv.research;

import android.util.Log;
import android.widget.Toast;

import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.network.RequestLyricInterface;
import com.fresher.tronnv.research.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Utils {
    public static final String BASE_URL = "http://www.mocky.io/v2/";
    private static RequestLyricInterface requestLyricInterface;
    private static List<MusicLyric> musicLyrics;

    public static RequestLyricInterface getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(RequestLyricInterface.class);
    }
    public static RequestLyricInterface getAPIServiceRxJava() {

        return RetrofitClient.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
    }
    public static List<MusicLyric> loadJSON() {

        requestLyricInterface = Utils.getAPIServiceRxJava();
        requestLyricInterface.register()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<MusicLyric>>(){
                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(getBaseContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Debug", ": " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        //Toast.makeText(getBaseContext(), "Get data success! ", Toast.LENGTH_SHORT).show();
                        Log.e("Debug", ": " + "Get data success! ");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MusicLyric> musicLyric) {
                        musicLyrics = musicLyric;
                        Log.i("Debug", "post submitted to API: " + musicLyric.get(0).toString());
                    }
                });
        return musicLyrics;
    }
}
