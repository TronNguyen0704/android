package com.fresher.tronnv.data_layer.network;

import com.fresher.tronnv.models.MusicLyric;
import com.fresher.tronnv.models.RecordChart;
import com.fresher.tronnv.models.Track;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */

/**
 * With Retrofit 2, endpoints are defined inside of an interface using special retrofit annotations
 * to encode details about the parameters and request method.
 * */
public interface RequestLyricInterface {
    @GET("5b04d1942f00003232e7aaa4")
    Observable<List<MusicLyric>> register();
    @GET("5b110c532f0000730034f1fe")
    Observable<List<RecordChart>> recorchart();
    @GET("5b18ed773000005b00da144a")
    Observable<List<Track>> track();
}
