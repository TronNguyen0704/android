package com.fresher.tronnv.research;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.fresher.tronnv.research.action.main";
        public static String INIT_ACTION = "com.fresher.tronnv.research.action.init";
        public static String PREV_ACTION = "com.fresher.tronnv.research.action.prev";
        public static String PLAY_ACTION = "com.fresher.tronnv.research.action.play";
        public static String NEXT_ACTION = "com.fresher.tronnv.research.action.next";
        public static String STARTFOREGROUND_ACTION = "com.fresher.tronnv.research.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.fresher.tronnv.research.action.stopforeground";

    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
    public static List<String> generatorIntegers(){
        List<String> items = new ArrayList<>();
        items.add("0");
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");
        items.add("7");
        items.add("8");
        items.add("9");
        items.add("10");
        items.add("11");
        items.add("71");
        items.add("82");
        items.add("93");
        items.add("104");
        items.add("115");
        items.add("30");
        items.add("14");
        items.add("24");
        items.add("34");
        items.add("44");
        items.add("54");
        items.add("64");
        items.add("74");
        items.add("84");
        items.add("94");
        items.add("102");
        items.add("114");
        items.add("471");
        items.add("842");
        items.add("943");
        return items;
    }
    public static List<String> generatorIntegers2(){
        List<String> items = new ArrayList<>();
        items.add("0");
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");
        items.add("7");
        items.add("8");
        items.add("9");
        items.add("10");
        items.add("11");
        items.add("71");
        items.add("82");
        items.add("93");
        items.add("104");
        items.add("115");
        items.add("30");
        items.add("14");
        items.add("24");
        items.add("34");
        items.add("44");
        items.add("54");
        items.add("64");
        items.add("74");
        items.add("84");
        items.add("94");
        items.add("102");
        items.add("114");
        items.add("471");
        items.add("842");
        items.add("943");
        items.set(new Random().nextInt(11), "1111");
        return items;
    }
}
