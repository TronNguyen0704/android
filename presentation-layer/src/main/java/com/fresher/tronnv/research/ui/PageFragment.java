package com.fresher.tronnv.research.ui;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.research.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

public class PageFragment extends Fragment {

    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";
    private static final String THUMB = "Thumb";
    private String mName;
    private String mDescription;
    private String mThumb;
    public PageFragment(){

    }
    public static PageFragment sInstance(String name,String desc,String thumb ){
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(NAME,name);
        args.putString(DESCRIPTION,desc);
        args.putString(THUMB,thumb);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            mName = args.getString(NAME);
            mDescription = args.getString(DESCRIPTION);
            mThumb = args.getString(THUMB);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource file
        final View rootView = inflater.inflate(R.layout.slider_item, container, false);
        TextView textView = rootView.findViewById(R.id.tv_title);
        TextView textDes = rootView.findViewById(R.id.tv_description);
        ImageView img = rootView.findViewById(R.id.img_avatar);
        textView.setText(mName);
        textDes.setText(mDescription);
        Glide.with(getActivity()).load(mThumb)
                .apply(RequestOptions.bitmapTransform(
                new VignetteFilterTransformation(
                        new PointF(0.5f, 0.5f),
                        new float[]{0f, 0f, 0f}, 0.1f, 0.75f)))
                .into(img);
        return rootView;
    }
}
