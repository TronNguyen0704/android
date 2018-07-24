package com.fresher.tronnv.research.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.PointF;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

/**
 */
public class HeaderPageAdapter extends PagerAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<Track> mTracks;
    private static HashMap<String,View> sViewCache;
    public HeaderPageAdapter(Activity context) {
        this.mContext = context;
        mInflater = context.getLayoutInflater();
        mTracks = new ArrayList<>();
        if(sViewCache == null){
            sViewCache = new HashMap<>();
        }
    }
    public void setTrackList(List<Track> trackList) {
        this.mTracks.addAll(trackList);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = mInflater.inflate(R.layout.slider_item, view, false);
        if(!sViewCache.containsKey(mTracks.get(position).getTitle())) {
            ImageView myImage = myImageLayout
                    .findViewById(R.id.img_avatar);
            Glide.with(mContext)
                    .load(mTracks.get(position).getThumb())
                    .apply(RequestOptions.bitmapTransform(
                            new VignetteFilterTransformation(
                                    new PointF(0.5f, 0.5f),
                                    new float[]{0f, 0f, 0f}, 0.1f, 0.75f)))
                    .into(myImage);
            TextView name = myImageLayout.findViewById(R.id.tv_title);
            name.setText(mTracks.get(position).getTitle());
            TextView des = myImageLayout.findViewById(R.id.tv_description);
            des.setText(mTracks.get(position).getDescription());
            view.addView(myImageLayout);
            sViewCache.put(mTracks.get(position).getTitle(),myImageLayout);
            return myImageLayout;
        }
        if(view.indexOfChild(sViewCache.get(mTracks.get(position).getTitle())) == -1){
            view.addView(sViewCache.get(mTracks.get(position).getTitle()));
        }
        return sViewCache.get(mTracks.get(position).getTitle());
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }

    @Nullable
    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void registerDataSetObserver(@NonNull DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(@NonNull DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
