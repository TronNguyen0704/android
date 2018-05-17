package com.fresher.tronnv.research.mudule;

import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.network.RequestLyricInterface;
import com.fresher.tronnv.research.network.RetrofitClient;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class NetModule {
    private RetrofitClient retrofitClient;
    public NetModule(RetrofitClient retrofitClient){
        this.retrofitClient = retrofitClient;
    }
    @Provides
    public RetrofitClient provideRetro(){
        return retrofitClient;
    }
}
