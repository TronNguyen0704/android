package com.fresher.tronnv.research.data;

import android.util.Log;

import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.network.RequestLyricInterface;
import com.fresher.tronnv.research.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.fresher.tronnv.research.Utils.BASE_URL;

/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
@Singleton
public class DataManager {
    private List<MusicLyric> musicLyrics;
    private RequestLyricInterface requestLyricInterface;
    @Inject
    public DataManager(){
    }
    public List<MusicLyric> getMusicLyrics(){
        return musicLyrics;
    }
    public void setMusicLyrics(List<MusicLyric> lyrics){
         musicLyrics = lyrics;
    }

    //Use RxJava and Retrofit to get data from server
    public void getData(RetrofitClient retrofit) {

        requestLyricInterface = retrofit.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
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
                        setMusicLyrics(musicLyric);
                        Log.i("Debug", "post submitted to API: " + musicLyric.get(0).toString());
                    }
                });
    }
}
