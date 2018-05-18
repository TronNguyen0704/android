package com.fresher.tronnv.research.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.component.DaggerNetComponent;
import com.fresher.tronnv.research.component.NetComponent;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.mudule.NetModule;
import com.fresher.tronnv.research.network.NetworkChangeReceiver;
import com.fresher.tronnv.research.network.RetrofitClient;

import javax.inject.Inject;


/**
 * Created by NGUYEN VAN TRON on 05/17/18.
 */
public class WelcomeActivity extends AppCompatActivity{

    private NetworkChangeReceiver receiver;
    private NetComponent netComponent;
    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        receiver = new NetworkChangeReceiver();
        receiver.setContext(this);
        final IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);
        if(receiver.isConnected()) {
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

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    public void goToMain(View v) {
        //Checking network
        if(receiver.isConnected()) {
            if (netComponent == null) {
                RetrofitClient retrofitClient = new RetrofitClient();
                netComponent = DaggerNetComponent
                        .builder()
                        .netModule(new NetModule(retrofitClient))
                        .build();
                netComponent.getDataManager().restAPIToGetData(retrofitClient);

            }
            if(netComponent.getDataManager().getMusicLyrics() != null) {
                Utils.musicLyrics = netComponent.getDataManager().getMusicLyrics();
                startActivity(new Intent(this, MainActivity.class));
            }else{
                Toast.makeText(this,"Data is loading...",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"No Internet connection",Toast.LENGTH_SHORT).show();
        }
    }

}