package com.fresher.tronnv.research;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
public class GlideHelper {
    private Context mCotext;
    private RequestBuilder sRequestBuilder;
    public GlideHelper(Context context) {
        mCotext = context;
        sRequestBuilder = Glide.with(mCotext).asDrawable();
    }

    public RequestBuilder getRequestBuilder() {
        return sRequestBuilder;
    }

    public static RequestOptions requestOptionForHeader(){
        return new RequestOptions().transform(new VignetteFilterTransformation(
                new PointF(0.5f, 0.5f),
                new float[]{0f, 0f, 0f}, 0.1f, 0.75f));
    }
    public static RequestOptions requestAvatar(){
        return new RequestOptions().transform(new RoundedCorners(10));
    }
    public static RequestBuilder requestBuilderAvatar(String avatar, View view){
        return Glide.with(view).setDefaultRequestOptions(requestAvatar()).load(avatar);
    }
    public static RequestBuilder requestBuilderImage(String url, View view){
        return Glide.with(view).setDefaultRequestOptions(requestAvatar()).load(url);
    }
    public static RequestBuilder requestBuilderForHeader(String url, View view){
        return Glide.with(view).setDefaultRequestOptions(requestOptionForHeader()).load(url);
    }
    public static void load(String url, Boolean loadOnlyFromCache, ImageView imageView){
        RequestListener listener = new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };
        RequestOptions requestOptions = RequestOptions.placeholderOf(R.drawable.ic_home_black_24dp)
                .centerCrop()
                .placeholder(R.drawable.ic_74)
                .error(R.drawable.ic_74)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .onlyRetrieveFromCache(loadOnlyFromCache);
        Glide.with(imageView)
                .load(url)
                .apply(requestOptions)
                .listener(listener)
                .into(imageView);
    }
}
