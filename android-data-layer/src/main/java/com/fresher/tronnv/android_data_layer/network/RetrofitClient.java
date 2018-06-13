package com.fresher.tronnv.android_data_layer.network;

/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * Creating the Retrofit instance
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    public RetrofitClient(){

    }
    /* To send out network requests to an API,
     * we need to use the Retrofit builder class and specify the base URL for the service.
     */
    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getClientRxJava(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
