package com.fresher.tronnv.research.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.component.DaggerNetComponent;
import com.fresher.tronnv.research.component.NetComponent;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.mudule.NetModule;
import com.fresher.tronnv.research.network.RetrofitClient;

import javax.inject.Inject;


/**
 * Created by NGUYEN VAN TRON on 05/17/18.
 */
public class WelcomeActivity extends AppCompatActivity{

    private NetComponent netComponent;
    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(netComponent == null){
            RetrofitClient retrofitClient = new RetrofitClient();
            netComponent = DaggerNetComponent //This class
                    .builder()
                    .netModule(new NetModule(retrofitClient))
                    .build();
            netComponent.getDataManager().getData(retrofitClient);

        }
    }
    public void goToMain(View v) {
        Utils.musicLyrics = netComponent.getDataManager().getMusicLyrics();
        startActivity(new Intent(this,MainActivity.class));
    }
}
