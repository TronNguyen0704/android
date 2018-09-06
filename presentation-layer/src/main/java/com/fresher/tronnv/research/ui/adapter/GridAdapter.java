package com.fresher.tronnv.research.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.research.GlideHelper;
import com.fresher.tronnv.research.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class GridAdapter extends BaseAdapter {
    private List<String> mImages;
    private Context mContext;
    public void setImages(List<String> images){
        mImages = images;
    }
    public GridAdapter(Context context){
        mImages = new ArrayList<>();
        mContext = context;
    }
    @Override
    public int getCount() {
        if(mImages!= null)
            return mImages.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mImages!= null)
            return mImages.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(mImages!= null)
            return position;
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView  = inflater.inflate(R.layout.grid_item, null);
        ImageView imageView = convertView.findViewById(R.id.img_chart);
        imageView.setPadding(8, 8, 0, 8);
//        Glide.with(mContext)
//                .load(mImages.get(position))
//                .apply(RequestOptions.bitmapTransform(
//                        new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
//                .into(imageView);
        GlideHelper.requestBuilderImage(mImages.get(position),imageView).into(imageView);
        return convertView;
    }
}
