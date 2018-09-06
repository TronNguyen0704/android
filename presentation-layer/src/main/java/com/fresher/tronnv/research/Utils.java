package com.fresher.tronnv.research;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Build;
import android.support.v4.text.PrecomputedTextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fresher.tronnv.research.ui.ExpandableTextView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Utils {
    static Executor sExecutor = Executors.newSingleThreadExecutor();
    private static Map<String,Future<PrecomputedTextCompat>> sPrecomputedTextCompatDictionary = new HashMap<>();
    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.icon_lyrics, options);
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }

    public static Bitmap getBitmapFromURL(String src, Context context) {
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(src)
                    .submit(512, 512)
                    .get();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int getScreenHight(Activity activity){
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
    public static final void asyncSetText3(TextView textView, CharSequence longString){
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            final PrecomputedText.Params params2 = textView.getTextMetricsParams();
            final Reference reference = new WeakReference<>(textView);
            sExecutor.execute(() -> {
                TextView textView2 = (TextView) reference.get();
                if (textView2 == null) return;
                final PrecomputedText expensiveText = PrecomputedText.create(longString, params2);
                textView.post(() -> {
                    TextView textView3 = (TextView) reference.get();
                    if (textView3 == null) return;
                    textView.setText(expensiveText);
                });
            });
            return;
        }
        Future<PrecomputedTextCompat> future ;
        if(sPrecomputedTextCompatDictionary.containsKey(longString.toString())){
            future = sPrecomputedTextCompatDictionary.get(longString.toString());
        }
        else {
            PrecomputedTextCompat.Params params = TextViewCompat.getTextMetricsParams(textView);
//            sExecutor.execute(()->ExpandableTextView.doResizeTextView(textView, 4, "View more", true));
            future = PrecomputedTextCompat.getTextFuture(
                    longString,
                    params, null);
            sPrecomputedTextCompatDictionary.put(longString.toString(),future);
        }
        ((AppCompatTextView)textView).setTextFuture(future);
    }
    public static final void asyncSetText(TextView textView, String longString){
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            final PrecomputedText.Params params2 = textView.getTextMetricsParams();
            final Reference reference = new WeakReference<>(textView);
            sExecutor.execute(() -> {
                TextView textView2 = (TextView) reference.get();
                if (textView2 == null) return;
                final PrecomputedText expensiveText = PrecomputedText.create(longString, params2);
                textView.post(() -> {
                    TextView textView3 = (TextView) reference.get();
                    if (textView3 == null) return;
                    textView.setText(expensiveText);
                });
            });
            return;
        }
        Future<PrecomputedTextCompat> future ;
        if(sPrecomputedTextCompatDictionary.containsKey(longString)){
            future = sPrecomputedTextCompatDictionary.get(longString);
        }
        else {
            PrecomputedTextCompat.Params params = TextViewCompat.getTextMetricsParams(textView);
            future = PrecomputedTextCompat.getTextFuture(
                    longString,
                    params, null);
            sPrecomputedTextCompatDictionary.put(longString,future);
        }

//        PrecomputedTextCompat spannable;
//            spannable = PrecomputedTextCompat.create(longString, params);
        ((AppCompatTextView)textView).setTextFuture(future);
//        PrecomputedTextCompat spannable = PrecomputedTextCompat.create(longString, params);
//        TextViewCompat.setPrecomputedText(textView,spannable);
    }
    public static final void asyncSetText2(TextView textView, String longString, int position){
        if(position % 2 == 0){
            textView.setTextSize(14);
            textView.setTextColor(Color.BLUE);
        }else{
            textView.setTextSize(16);
            textView.setTextColor(Color.YELLOW);
        }
        if (android.os.Build.VERSION.SDK_INT >= 28) {
//            ((AppCompatTextView)textView).setTextFuture(PrecomputedTextCompat.getTextFuture(
//            longString,
//            TextViewCompat.getTextMetricsParams(textView),null));
            final PrecomputedText.Params params2 = textView.getTextMetricsParams();
            final Reference reference = new WeakReference<>(textView);
            sExecutor.execute(() -> {
                TextView textView2 = (TextView) reference.get();
                if (textView2 == null) return;
                final PrecomputedText expensiveText = PrecomputedText.create(longString, params2);
                textView.post(() -> {
                    TextView textView3 = (TextView) reference.get();
                    if (textView3 == null) return;
                    textView.setText(expensiveText);
                });
            });
            return;
        }
        Future<PrecomputedTextCompat> future ;
        if(sPrecomputedTextCompatDictionary.containsKey(longString)){
            future = sPrecomputedTextCompatDictionary.get(longString);
        }
        else {
            PrecomputedTextCompat.Params params = TextViewCompat.getTextMetricsParams(textView);
            future = PrecomputedTextCompat.getTextFuture(
                    longString,
                    params,null);
            sPrecomputedTextCompatDictionary.put(longString,future);
        }

//        PrecomputedTextCompat spannable;
//            spannable = PrecomputedTextCompat.create(longString, params);
        ((AppCompatTextView)textView).setTextFuture(future);
//        PrecomputedTextCompat spannable = PrecomputedTextCompat.create(longString, params);
//        TextViewCompat.setPrecomputedText(textView,spannable);


    }
}
