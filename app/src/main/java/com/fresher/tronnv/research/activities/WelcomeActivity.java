package com.fresher.tronnv.research.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.component.NetComponent;
import com.fresher.tronnv.research.data.DataManager;

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
//        if(netComponent == null){
//            netComponent = Dagger_NetComponent
//                    .builder()
//                    .netModule(new NetModule())
//                    .build();
//        }
    }

    public void goToMain(View v) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
