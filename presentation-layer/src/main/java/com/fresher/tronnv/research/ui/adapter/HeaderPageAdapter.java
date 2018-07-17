package com.fresher.tronnv.research.ui.adapter;

import android.app.Activity;
import android.graphics.PointF;
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
import java.util.List;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

public class HeaderPageAdapter extends PagerAdapter {

    private LayoutInflater mInflater;
    private Activity mContext;
    private List<Track> mTracks;

    public HeaderPageAdapter(Activity context) {
        this.mContext = context;
        mInflater = context.getLayoutInflater();
        mTracks = new ArrayList<>();
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
        Glide.get(view.getContext()).clearMemory();
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
