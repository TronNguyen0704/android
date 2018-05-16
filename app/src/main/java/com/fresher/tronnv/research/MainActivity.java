package com.fresher.tronnv.research;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fresher.tronnv.research.model.MusicLyrics;
import com.fresher.tronnv.research.network.RequestLyricInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RequestLyricInterface requestLyricInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestLyricInterface = Utils.getAPIService();
        sendPost("Attention","Charlie Puth","You've been runnin' round, runnin' round, runnin' round throwing that dirt all on my name \n" +
                "'Cause you knew that I, knew that I, knew that I'd call you up \n" +
                "You've been going round, going round, going round every party in LA \n" +
                "'Cause you knew that I, knew that I, knew that I'd be at one ");

    }
    public void sendPost(String name, String author, String lyric) {
        requestLyricInterface.savePost(name, author, lyric).enqueue(new Callback<MusicLyrics>() {
            @Override
            public void onResponse(Call<MusicLyrics> call, Response<MusicLyrics> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i("Debug", "post submitted to API." + response.body().toString());
                }
            }


            @Override
            public void onFailure(Call<MusicLyrics> call, Throwable t) {
                Log.e("Debug", "Unable to submit post to API.");
            }
        });
    }

    public void showResponse(String response) {

    }
}
