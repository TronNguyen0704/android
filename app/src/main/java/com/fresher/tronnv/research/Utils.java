package com.fresher.tronnv.research;

import android.util.Log;
import android.widget.Toast;

import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.network.RequestLyricInterface;
import com.fresher.tronnv.research.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class Utils {
    public static final String BASE_URL = "http://www.mocky.io/v2/";
    public static List<MusicLyric> musicLyrics;
}
