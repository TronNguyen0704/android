package com.fresher.tronnv.research.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v4.view.PagerAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.data.source.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.SupportRSBlurTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

public class HeaderPageAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Activity context;
    private List<Track> tracks;
    public HeaderPageAdapter(Activity context) {
        this.context = context;
        inflater = context.getLayoutInflater();
        tracks = new ArrayList<>();

    }
    public void setTrackList(List<Track> trackList) {
        if(this.tracks == null) {
            this.tracks = trackList;
        }
        else{
            tracks = trackList;
        }
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return tracks.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slider_item, view, false);
        ImageView myImage =  myImageLayout
                .findViewById(R.id.img_avatar);

        Glide.with(context)
                .load(tracks.get(position).getThumb())
                .apply(RequestOptions.bitmapTransform(
                        new VignetteFilterTransformation(
                                new PointF(0.5f,0.5f),
                                new float[]{0f,0f,0f},0.1f,0.75f)))
                .into(myImage);
        TextView name = myImageLayout.findViewById(R.id.tv_title);
        name.setText(tracks.get(position).getTitle());
        TextView des = myImageLayout.findViewById(R.id.tv_description);
        des.setText(tracks.get(position).getDescription());
        view.addView(myImageLayout);
        Glide.get(view.getContext()).clearMemory();
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
