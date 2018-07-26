package com.fresher.tronnv.research.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.ui.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter{
    private List<Track> mTracks;
    public PageAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        mTracks = new ArrayList<>();
    }
    public void setTracks(List<Track> mTracks) {
        this.mTracks = mTracks;
    }
    @Override
    public Fragment getItem(int position) {
        return PageFragment.sInstance(mTracks.get(position).getTitle(),
                mTracks.get(position).getDescription(),
                mTracks.get(position).getThumb());
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }
}
