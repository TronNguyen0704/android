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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.service.NetworkChangeReceiver;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;
import com.fresher.tronnv.research.ui.ExpandableTextView;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Maybe;
import io.reactivex.ObservableEmitter;
import rx.Observable;
import rx.Single;


/**
 * Created by NGUYEN VAN TRON on 05/17/18.
 */
public class WelcomeActivity extends AppCompatActivity{

    private NetworkChangeReceiver mReceiver;
    private ApplicationPresenter mApplicationPresenter;
    enum Irrelevant { INSTANCE; }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mReceiver = new NetworkChangeReceiver();
        mReceiver.setContext(this);
        final IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        //Broadcast mReceiver
        registerReceiver(mReceiver, filter);
        mApplicationPresenter = new ApplicationPresenterImpl(getBaseContext());
        mApplicationPresenter.loadMusicData();
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.scale);
        shake.setRepeatCount(ObjectAnimator.INFINITE);
        shake.setRepeatMode(ObjectAnimator.REVERSE);
        Button startBtn = findViewById(R.id.btn_start);
        startBtn.setAnimation(shake);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void goToMain(View v) {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            startActivity(new Intent(this, MainActivity.class));
        }
        //Checking network
        if(mReceiver.isConnected(getBaseContext())) {
            if(mApplicationPresenter.isData()) {
                startActivity(new Intent(this, MainActivity.class));
            }else{
                mApplicationPresenter.loadMusicData();
                Toast.makeText(this,"Data is loading...",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if(!mApplicationPresenter.isData()) {
                Toast.makeText(this,"No Internet connection",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Loading local data",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }
}
