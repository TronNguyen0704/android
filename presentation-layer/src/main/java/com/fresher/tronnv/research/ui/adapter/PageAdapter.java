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
    private List<PageFragment> mPageFragments;
    public PageAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        mTracks = new ArrayList<>();
        mPageFragments = new ArrayList<>();
    }
    public void setTracks(List<Track> mTracks) {
        this.mTracks = mTracks;
        cachePageFrament();
    }
    private void cachePageFrament(){
        Track track;
        for (int i = 0; i < mTracks.size(); i++) {
            track = mTracks.get(i);
            PageFragment pageFragment = PageFragment.sInstance(track.getTitle(),
                    track.getDescription(),
                    track.getThumb());
            if (pageFragment != null)
                mPageFragments.add(pageFragment);
        }
    }
    @Override
    public Fragment getItem(int position) {
        return mPageFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }
}
