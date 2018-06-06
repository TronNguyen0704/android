package com.fresher.tronnv.research.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.network.NetworkChangeReceiver;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;



/**
 * Created by NGUYEN VAN TRON on 05/17/18.
 */
public class WelcomeActivity extends AppCompatActivity{

    private NetworkChangeReceiver receiver;
    private ApplicationPresenter applicationPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        receiver = new NetworkChangeReceiver();
        receiver.setContext(this);
        final IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        //Broadcast receiver
        registerReceiver(receiver, filter);

        applicationPresenter = new ApplicationPresenterImpl(getBaseContext());
        applicationPresenter.requestMusic();
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.scale);
        shake.setRepeatCount(ObjectAnimator.INFINITE);
        shake.setRepeatMode(ObjectAnimator.REVERSE);
        Button startBtn = findViewById(R.id.btn_start);
        startBtn.setAnimation(shake);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void goToMain(View v) {
        //Checking network
        if(receiver.isConnected(getBaseContext())) {
            if(applicationPresenter.requestMusic() != null) {
                startActivity(new Intent(this, MainActivity.class));
            }else{
                Toast.makeText(this,"Data is loading...",Toast.LENGTH_SHORT).show();
            }
        }
        else{

            if(applicationPresenter.requestMusic() == null ||(applicationPresenter.requestMusic().size() ==0) ) {
                Toast.makeText(this,"No Internet connection",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Loading local data",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }

}
