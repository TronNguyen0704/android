package com.fresher.tronnv.research;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

public class Utils {
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
}
