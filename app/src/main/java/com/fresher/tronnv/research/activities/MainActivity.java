package com.fresher.tronnv.research.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.network.RequestLyricInterface;
import com.fresher.tronnv.research.ui.LyricsFragment;
import com.fresher.tronnv.research.ui.PlayListFragment;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements PlayListFragment.OnItemLyricClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Event when click on a item in playlist
    @Override
    public void onItemSelected(int position, int idSong, String filter) {
        Log.i("Position clicked = " , "" +position);
        Bundle b = new Bundle();
        b.putInt("Index", position);
        b.putInt("ID", idSong);
        b.putString("Filter", filter);
        final Intent intent = new Intent(this, LyricActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
