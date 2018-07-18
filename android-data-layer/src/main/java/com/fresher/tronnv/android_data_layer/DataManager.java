package com.fresher.tronnv.android_data_layer;




import android.content.Context;

import com.fresher.tronnv.android_data_layer.json.JSONManager;
import com.fresher.tronnv.android_data_layer.network.RequestLyricInterface;
import com.fresher.tronnv.android_data_layer.network.RetrofitClient;
import com.fresher.tronnv.android_data_layer.network.component.DaggerNetComponent;
import com.fresher.tronnv.android_data_layer.network.component.NetComponent;
import com.fresher.tronnv.android_data_layer.network.module.NetModule;
import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.fresher.tronnv.android_data_layer.Constants.BASE_URL;


/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
@Singleton
public class DataManager {
    private List<MusicLyric> mMusicLyrics = null;
    private List<Track> mTracks = null;
    private List<RecordChart> mRecordCharts = null;
    private RequestLyricInterface mRequestLyricInterface;
    private Context mContext;
    private NetComponent mNetComponent;

    public void setContext(Context context) {
        this.mContext = context;
    }
    @Inject
    public DataManager(){
       if( mMusicLyrics == null){
           mMusicLyrics = new ArrayList<>();
       }
        if( mTracks == null){
            mTracks = new ArrayList<>();
        }
        if( mRecordCharts == null){
            mRecordCharts = new ArrayList<>();
        }
    }
    public List<MusicLyric> getMusicLyrics() {
        if(mMusicLyrics != null && (mMusicLyrics.size() > 0))
            return mMusicLyrics;
        if(JSONManager.isDataPlaylistExist()) { //if file is exist -> continue read data
            return JSONManager.JsonSimpleReader();
        }
        return null;
    }
    public List<Track> getTracks() {
        if(mTracks != null && (mTracks.size() > 0))
            return mTracks;
        if(JSONManager.isDataTrackExist()) { //if file is exist -> continue read data
            return JSONManager.JsonTrackReader();
        }
        return null;
    }
    public List<RecordChart> getRecordCharts() {
        if(mRecordCharts != null && (mRecordCharts.size() > 0))
            return mRecordCharts;
        if(JSONManager.isDataRankExist()) { //if file is exist -> continue read data
            return JSONManager.JsonRecordChartReader();
        }
        return null;
    }
    public List<MusicLyric> getMusicByName(String filter) {
        List<MusicLyric> filterList = new ArrayList<>();
        mMusicLyrics.addAll(getMusicLyrics());
            if (filter.endsWith("null") || filter.endsWith("")){
                return mMusicLyrics;
            }
            else {
                for (MusicLyric musicLyric : mMusicLyrics) {
                    if (filter.toLowerCase().contains(musicLyric.getName().toLowerCase())) {
                        filterList.add(musicLyric);
                    }
                }
                return filterList;
            }
    }
    public MusicLyric getSongById(int id) {
        mMusicLyrics.addAll(getMusicLyrics());
        for (MusicLyric musicLyric : mMusicLyrics){
            if(musicLyric.getId() == id){
                return musicLyric;
            }
        }
        return null;
    }
    public void setMusicLyrics(List<MusicLyric> lyrics){
         this.mMusicLyrics.addAll(lyrics);
    }
    public void setTracks(List<Track> tracks){
        this.mTracks.addAll(tracks);
    }
    public void setRecordCharts(List<RecordChart> recordCharts){
        this.mRecordCharts.addAll(recordCharts);
    }
    public void loadDataFromServer(){
        if(!JSONManager.isDataPlaylistExist()) { //!JSONManager.isDataPlaylistExist()
            if (!(mMusicLyrics != null && mMusicLyrics.size() > 0)) {
                    RetrofitClient retrofitClient = new RetrofitClient();
                    if(mNetComponent == null){
                        mNetComponent = DaggerNetComponent
                                .builder()
                                .netModule(new NetModule(retrofitClient))
                                .build();
                    }
                mNetComponent.getDataManager().restAPIToGetData(retrofitClient);
                    //restAPIToGetData(retrofitClient);
            }
        }
    }
    //Use RxJava and Retrofit to get data from server
    public void restAPIToGetData(RetrofitClient retrofit) {
        mRequestLyricInterface = retrofit.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
        mRequestLyricInterface.register()
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
                        //Log.i("Debug", "post submitted to API: " + mMusicLyrics.toString());
                        //Save file By FileWriter
                        JSONManager.JsonSimpleWriter(mMusicLyrics);
                    }
                })) ;
        mRequestLyricInterface.recorchart()
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
                        //Log.i("Debug", "post submitted to API: " + mRecordCharts.get(0).getName());
                        //Save file By FileWriter
                        JSONManager.JsonRecordChartWriter(recordCharts);
                    }
                })) ;
        mRequestLyricInterface.track()
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
                        //Log.i("Debug", "post submitted to API: " + mTracks.get(0).getDescription());
                        //Save file By FileWriter
                        JSONManager.JsonTrackWriter(tracks);
                    }
                })) ;
    }

    public void loadMusicData() {
        if(!JSONManager.isDataPlaylistExist()) { //!JSONManager.isDataPlaylistExist()
            if (!(mMusicLyrics != null && mMusicLyrics.size() > 0)) {
                RetrofitClient retrofitClient = new RetrofitClient();
                restAPIToGetMusicData(retrofitClient);
            }
        }
    }

    private void restAPIToGetMusicData(RetrofitClient retrofitClient) {
        mRequestLyricInterface = retrofitClient.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
        mRequestLyricInterface.register()
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
                        //Log.i("Debug", "post submitted to API: " + mMusicLyrics.toString());
                        //Save file By FileWriter
                        JSONManager.JsonSimpleWriter(mMusicLyrics);
                    }
                })) ;
    }

    public void loadRecordChartData() {
        if(!JSONManager.isDataRankExist()) { //!JSONManager.isDataPlaylistExist()
            if (!(mMusicLyrics != null && mMusicLyrics.size() > 0)) {
                RetrofitClient retrofitClient = new RetrofitClient();
                restAPIToGetRecordChartData(retrofitClient);
            }
        }
    }

    private void restAPIToGetRecordChartData(RetrofitClient retrofitClient) {
        mRequestLyricInterface = retrofitClient.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
        mRequestLyricInterface.recorchart()
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
                        //Log.i("Debug", "post submitted to API: " + mRecordCharts.get(0).getName());
                        //Save file By FileWriter
                        JSONManager.JsonRecordChartWriter(recordCharts);
                    }
                })) ;
    }

    public void loadTrackData() {
        if(!JSONManager.isDataTrackExist()) { //!JSONManager.isDataPlaylistExist()
            if (!(mTracks != null && mTracks.size() > 0)) {
                RetrofitClient retrofitClient = new RetrofitClient();
                restAPIToGetTrackData(retrofitClient);
            }
        }
    }

    private void restAPIToGetTrackData(RetrofitClient retrofitClient) {
        mRequestLyricInterface = retrofitClient.getClientRxJava(BASE_URL).create(RequestLyricInterface.class);
        mRequestLyricInterface.track()
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
                        //Log.i("Debug", "post submitted to API: " + mTracks.get(0).getDescription());
                        //Save file By FileWriter
                        JSONManager.JsonTrackWriter(tracks);
                    }
                })) ;
    }
}
