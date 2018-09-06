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
}
