package com.fresher.tronnv.data_layer;


import com.fresher.tronnv.data_layer.json.JSONManager;
import com.fresher.tronnv.models.MusicLyric;
import com.fresher.tronnv.models.RecordChart;
import com.fresher.tronnv.models.Track;
import com.fresher.tronnv.data_layer.network.RequestLyricInterface;
import com.fresher.tronnv.data_layer.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.fresher.tronnv.data_layer.Constants.BASE_URL;


/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class DataManager {
    private List<MusicLyric> musicLyrics = null;
    private List<Track> tracks = null;
    private List<RecordChart> recordCharts = null;
    private RequestLyricInterface requestLyricInterface;

    public DataManager(){
       if( musicLyrics == null){
           musicLyrics = new ArrayList<>();
       }
        if( tracks == null){
            tracks = new ArrayList<>();
        }
        if( recordCharts == null){
            recordCharts = new ArrayList<>();
        }
    }
    public List<MusicLyric> getMusicLyrics() {
        if(musicLyrics!= null && (musicLyrics.size() > 0))
            return musicLyrics;
        if(JSONManager.isDataPlaylistExist()) { //if file is exist -> continue read data
            return JSONManager.JsonSimpleReader();
        }
        return null;
    }
    public List<Track> getTracks() {
        if(tracks!= null && (tracks.size() > 0))
            return tracks;
        if(JSONManager.isDataTrackExist()) { //if file is exist -> continue read data
            return JSONManager.JsonTrackReader();
        }
        return null;
    }
    public List<RecordChart> getRecordCharts() {
        if(recordCharts!= null && (recordCharts.size() > 0))
            return recordCharts;
        if(JSONManager.isDataRankExist()) { //if file is exist -> continue read data
            return JSONManager.JsonRecordChartReader();
        }
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
         this.musicLyrics.addAll(lyrics);
    }
    public void setTracks(List<Track> tracks){
        this.tracks.addAll(tracks);
    }
    public void setRecordCharts(List<RecordChart> recordCharts){
        this.recordCharts.addAll(recordCharts);
    }
    public void loadDataFromServer(){
        if(!JSONManager.isDataPlaylistExist()) { //!JSONManager.isDataPlaylistExist()
            if (!(musicLyrics != null && musicLyrics.size() > 0)) {
                    RetrofitClient retrofitClient = new RetrofitClient();
                    restAPIToGetData(retrofitClient);
            }
        }
    }
    //Use RxJava and Retrofit to get data from server
    public void restAPIToGetData(RetrofitClient retrofit) {
        requestLyricInterface = retrofit.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
        requestLyricInterface.register()
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .subscribe((new Observer<List<MusicLyric>>(){
                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(getBaseContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + e.getLocalizedMessage());
                    }
                    @Override
                    public void onComplete() {
                        //Toast.makeText(getBaseContext(), "Get data success! ", Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + "Get data success! ");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MusicLyric> musicLyric) {
                        setMusicLyrics(musicLyric);
                        //Log.i("Debug", "post submitted to API: " + musicLyrics.toString());
                        //Save file By FileWriter
                        JSONManager.JsonSimpleWriter(musicLyrics);
                    }
                })) ;
        requestLyricInterface.recorchart()
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .subscribe((new Observer<List<RecordChart>>(){
                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(getBaseContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + e.getLocalizedMessage());
                    }
                    @Override
                    public void onComplete() {
                        //Toast.makeText(getBaseContext(), "Get data success! ", Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + "Get data success! ");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<RecordChart> recordCharts) {
                        //Log.i("Debug", "post submitted to API: " + recordCharts.get(0).getName());
                        //Save file By FileWriter
                        JSONManager.JsonRecordChartWriter(recordCharts);
                    }
                })) ;
        requestLyricInterface.track()
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .subscribe((new Observer<List<Track>>(){
                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(getBaseContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + e.getLocalizedMessage());
                    }
                    @Override
                    public void onComplete() {
                        //Toast.makeText(getBaseContext(), "Get data success! ", Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + "Get data success! ");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Track> tracks) {
                        //Log.i("Debug", "post submitted to API: " + tracks.get(0).getDescription());
                        //Save file By FileWriter
                        JSONManager.JsonTrackWriter(tracks);
                    }
                })) ;
    }

    public void loadMusicData() {
        if(!JSONManager.isDataPlaylistExist()) { //!JSONManager.isDataPlaylistExist()
            if (!(musicLyrics != null && musicLyrics.size() > 0)) {
                RetrofitClient retrofitClient = new RetrofitClient();
                restAPIToGetMusicData(retrofitClient);
            }
        }
    }

    private void restAPIToGetMusicData(RetrofitClient retrofitClient) {
        requestLyricInterface = retrofitClient.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
        requestLyricInterface.register()
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .subscribe((new Observer<List<MusicLyric>>(){
                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(getBaseContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + e.getLocalizedMessage());
                    }
                    @Override
                    public void onComplete() {
                        //Toast.makeText(getBaseContext(), "Get data success! ", Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + "Get data success! ");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MusicLyric> musicLyric) {
                        setMusicLyrics(musicLyric);
                        //Log.i("Debug", "post submitted to API: " + musicLyrics.toString());
                        //Save file By FileWriter
                        JSONManager.JsonSimpleWriter(musicLyrics);
                    }
                })) ;
    }

    public void loadRecordChartData() {
        if(!JSONManager.isDataRankExist()) { //!JSONManager.isDataPlaylistExist()
            if (!(musicLyrics != null && musicLyrics.size() > 0)) {
                RetrofitClient retrofitClient = new RetrofitClient();
                restAPIToGetRecordChartData(retrofitClient);
            }
        }
    }

    private void restAPIToGetRecordChartData(RetrofitClient retrofitClient) {
        requestLyricInterface = retrofitClient.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
        requestLyricInterface.recorchart()
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .subscribe((new Observer<List<RecordChart>>(){
                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(getBaseContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + e.getLocalizedMessage());
                    }
                    @Override
                    public void onComplete() {
                        //Toast.makeText(getBaseContext(), "Get data success! ", Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + "Get data success! ");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<RecordChart> recordCharts) {
                        setRecordCharts(recordCharts);
                        //Log.i("Debug", "post submitted to API: " + recordCharts.get(0).getName());
                        //Save file By FileWriter
                        JSONManager.JsonRecordChartWriter(recordCharts);
                    }
                })) ;
    }

    public void loadTrackData() {
        if(!JSONManager.isDataTrackExist()) { //!JSONManager.isDataPlaylistExist()
            if (!(tracks != null && tracks.size() > 0)) {
                RetrofitClient retrofitClient = new RetrofitClient();
                restAPIToGetTrackData(retrofitClient);
            }
        }
    }

    private void restAPIToGetTrackData(RetrofitClient retrofitClient) {
        requestLyricInterface = retrofitClient.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
        requestLyricInterface.track()
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .subscribe((new Observer<List<Track>>(){
                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(getBaseContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + e.getLocalizedMessage());
                    }
                    @Override
                    public void onComplete() {
                        //Toast.makeText(getBaseContext(), "Get data success! ", Toast.LENGTH_SHORT).show();
                        //Log.e("Debug", ": " + "Get data success! ");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Track> tracks) {
                        setTracks(tracks);
                        //Log.i("Debug", "post submitted to API: " + tracks.get(0).getDescription());
                        //Save file By FileWriter
                        JSONManager.JsonTrackWriter(tracks);
                    }
                })) ;
    }
}
