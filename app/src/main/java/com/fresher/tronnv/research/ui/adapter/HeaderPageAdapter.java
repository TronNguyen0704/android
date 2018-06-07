package com.fresher.tronnv.research.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fresher.tronnv.research.R;

import java.util.ArrayList;

public class HeaderPageAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Activity context;

    private ArrayList<String> titles;
    private ArrayList<String> resources;
    public HeaderPageAdapter(Activity context) {
        this.context = context;
        inflater = context.getLayoutInflater();
        titles = new ArrayList<>();
        resources = new ArrayList<>();
        //Load data dummy : Test UI
        titles.add("Thương em thương lắm");
        titles.add("Vụt sáng thành vì sao");
        titles.add("Như là yêu mới lần đầu");
        titles.add("YG Family");
        titles.add("Cô gái nhà bên");
        resources.add(context.getResources().getString(R.string.image1));
        resources.add(context.getResources().getString(R.string.image2));
        resources.add(context.getResources().getString(R.string.image3));
        resources.add(context.getResources().getString(R.string.image4));
        resources.add(context.getResources().getString(R.string.image5));

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slider_item, view, false);
        ImageView myImage =  myImageLayout
                .findViewById(R.id.img_avatar);

        Glide.with(context)
                .load(resources.get(position))
                .into(myImage);
        TextView name = myImageLayout.findViewById(R.id.tv_title);
        name.setText(titles.get(position));
        view.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
