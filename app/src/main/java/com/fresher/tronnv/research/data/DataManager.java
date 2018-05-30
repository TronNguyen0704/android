package com.fresher.tronnv.research.data;

import android.util.Log;

import com.fresher.tronnv.research.component.DaggerNetComponent;
import com.fresher.tronnv.research.component.NetComponent;
import com.fresher.tronnv.research.data.json.JSONManager;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.mudule.NetModule;
import com.fresher.tronnv.research.network.RequestLyricInterface;
import com.fresher.tronnv.research.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.fresher.tronnv.research.Constants.BASE_URL;

/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
@Singleton
public class DataManager {
    private List<MusicLyric> musicLyrics = null;
    private RequestLyricInterface requestLyricInterface;
    private NetComponent netComponent;
    @Inject
    public DataManager(){
       if( musicLyrics == null){
           musicLyrics = new ArrayList<>();
       }
    }
    public List<MusicLyric> getMusicLyrics() {
        if(musicLyrics!= null && (musicLyrics.size() > 0))
            return musicLyrics;
        if(JSONManager.isExist()) //if file is exist -> continue read data
            return JSONManager.JsonSimpleReader();
        return null;
    }
    public List<MusicLyric> getMusicByName(String filter) {
        List<MusicLyric> filterList = new ArrayList<>();
        musicLyrics.addAll(getMusicLyrics());
            if (filter.endsWith("null") || filter.endsWith("")){
                return musicLyrics;
            }
            else {
                for (MusicLyric musicLyric : musicLyrics) {
                    if (filter.toLowerCase().contains(musicLyric.getName().toLowerCase())) {
                        filterList.add(musicLyric);
                    }
                }
                return filterList;
            }
    }
    public MusicLyric getSongById(int id) {
        musicLyrics.addAll(getMusicLyrics());
        for (MusicLyric musicLyric : musicLyrics){
            if(musicLyric.getId() == id){
                return musicLyric;
            }
        }
        return null;
    }
    public void setMusicLyrics(List<MusicLyric> lyrics){
         musicLyrics.addAll(lyrics);
    }
    public void loadDataFromServer(){
        if(!JSONManager.isExist()) {
            if (!(musicLyrics != null && musicLyrics.size() > 0)) {
                if (netComponent == null) {
                    RetrofitClient retrofitClient = new RetrofitClient();
                    netComponent = DaggerNetComponent
                            .builder()
                            .netModule(new NetModule(retrofitClient))
                            .build();
                    netComponent.getDataManager().restAPIToGetData(retrofitClient);
                }
            }
        }
    }
    //Use RxJava and Retrofit to get data from server
    public void restAPIToGetData(RetrofitClient retrofit) {
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
                        Log.i("Debug", "post submitted to API: " + musicLyrics.toString());
                        //Save file
                        JSONManager.JsonSimpleWriter(musicLyrics);
                    }
                }) ;
    }
}
