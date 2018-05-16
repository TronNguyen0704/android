package com.fresher.tronnv.research.network;
import com.fresher.tronnv.research.model.MusicLyrics;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import io.reactivex.Observable;
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
    @GET("lyrics")
    Observable<MusicLyrics> register();
    @POST("/posts")
    @FormUrlEncoded
    Call<MusicLyrics> savePost(@Field("name") String name,
                               @Field("author") String author,
                               @Field("lyric") String lyric);
    @POST("rxjava/posts")
    @FormUrlEncoded
    Observable<MusicLyrics> savePostRxJava(@Field("name") String title,
                              @Field("author") String body,
                              @Field("lyric") String userId);
}
