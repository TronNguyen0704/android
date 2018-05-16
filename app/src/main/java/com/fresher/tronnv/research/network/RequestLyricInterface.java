package com.fresher.tronnv.research.network;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import io.reactivex.Observable;
import retrofit2.http.PUT;
import retrofit2.http.Path;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */

/**
 * With Retrofit 2, endpoints are defined inside of an interface using special retrofit annotations
 * to encode details about the parameters and request method.
 * In addition, the return value is always a parameterized Call<T> object such as Call<User>.
 * If you do not need any type-specific response, you can specify return value as simply Call<ResponseBody>.
 * For instance, the interface defines each endpoint in the following way
 * */
public interface RequestLyricInterface {
    @GET("5afbf2c631000087007c5bb9")
    Observable<List<MusicLyric>> register();
}
