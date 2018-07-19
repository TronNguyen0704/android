package com.fresher.tronnv.research.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;
import com.fresher.tronnv.research.ui.adapter.HeaderPageAdapter;
import com.fresher.tronnv.research.ui.adapter.PageAdapter;
import com.fresher.tronnv.research.ui.adapter.RecordChartAdapter;
import com.fresher.tronnv.research.viewmodel.RecordChartViewModel;
import com.fresher.tronnv.research.viewmodel.TrackViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
/**dùng service để chạy MediaPlayer và dùng BroadcastReceiver để cập nhật giao diện của notification cũng như là UI nên tốc độ còn hơi chậm
 * A class PlayListFragment
 */
public class HomeFragment extends Fragment {

    public interface Loadfinish {
        void onFinish();
    }

    private int mCurrentPage = 0;
    private ViewPager mViewPager;
    private boolean mIsLoading = true;
    private boolean mIsLoadingPagerView = true;
    List<RecordChart> mRecordChartList;
    List<Track> mTracks;
    private RecordChartAdapter mRecordChartAdapter;
    private HeaderPageAdapter mHeaderPageAdapter;
    private PageAdapter mPageAdapter;
    private Loadfinish mContext;
    private ApplicationPresenter mApplicationPresenter;
    private Handler mHandler;
    private Runnable mUpdate;
    public HomeFragment() {
        mRecordChartList = new ArrayList<>();
        mTracks = new ArrayList<>();
        mApplicationPresenter = new ApplicationPresenterImpl(getContext());
        mApplicationPresenter.loadTrackData();
        mApplicationPresenter.loadRecordChartData();
    }
    public boolean isLoading() {
        return !(mIsLoading || mIsLoadingPagerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecordChartViewModel viewModel =
                ViewModelProviders.of(this).get(RecordChartViewModel.class);
        subscribeUI(viewModel);
        final TrackViewModel trackViewModel =
                ViewModelProviders.of(this).get(TrackViewModel.class);
        subscribeUI(trackViewModel);
    }

    private void subscribeUI(RecordChartViewModel viewModel) {
        viewModel.getRecordChart().observe(this, new Observer<List<RecordChart>>() {
            @Override
            public void onChanged(@Nullable List<RecordChart> recordCharts) {
                if (recordCharts != null) {
                    mRecordChartAdapter.setRecordChartList(recordCharts);
                    mRecordChartAdapter.notifyDataSetChanged();
                    mIsLoading = false;
                    if (isLoading()) {
                        mContext.onFinish();
                    }
                } else {
                    mIsLoading = true;
                }
            }
        });
    }

    private void subscribeUI(TrackViewModel viewModel) {
        viewModel.getTracks().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable List<Track> tracks) {
                if (tracks != null) {
                    mPageAdapter.setmTracks(tracks);
                    mPageAdapter.notifyDataSetChanged();
//                    mHeaderPageAdapter.setTrackList(tracks);
//                    mHeaderPageAdapter.notifyDataSetChanged();
                    mIsLoadingPagerView = false;
                    if (isLoading()) {
                       mContext.onFinish();
                    }
                } else {
                    mIsLoadingPagerView = true;
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Loadfinish) {
            this.mContext = (Loadfinish)context;
        }
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_record_chart, container, false);

        mViewPager = rootView.findViewById(R.id.view_pager);
        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(0, 0, 0, 0);
        //mHeaderPageAdapter = new HeaderPageAdapter(getActivity());
        mPageAdapter = new PageAdapter(getFragmentManager());
        //mViewPager.setAdapter(mHeaderPageAdapter);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setCurrentItem(0, true);
        mViewPager.setPageTransformer(false, (page, position) -> {
            int pageWidth = page.getWidth();
            float pageWidthTimesPosition = pageWidth * position;
            float absPosition = Math.abs(position);
            if (position <= -1.0f || position >= 1.0f) {


            } else if (position == 0.0f) {


            } else {

                View title = page.findViewById(R.id.tv_title);
                title.setAlpha(1.0f - absPosition);
                View description = page.findViewById(R.id.tv_description);
                description.setTranslationY(-pageWidthTimesPosition / 2f);
                description.setAlpha(1.0f - absPosition);
                View computer = page.findViewById(R.id.img_play);
                computer.setAlpha(1.0f - absPosition);
                computer.setTranslationX(-pageWidthTimesPosition * 1.5f);
                View avatar = page.findViewById(R.id.img_avatar);
                avatar.setAlpha(1.0f - absPosition);
                if (position < 0) {
                    // Create your out animation here
                } else {
                    // Create your in animation here
                }
            }

        });
        final RecyclerView listView = rootView.findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);
        mRecordChartAdapter = new RecordChartAdapter(getContext());
        //mRecordChartAdapter.setRecordChartList(mApplicationPresenter.getRecordCharts());
        //listView.setDivider(null);
        listView.setAdapter(mRecordChartAdapter);
        //mRecordChartAdapter.notifyDataSetChanged();

        // Auto start of viewpager
        mHandler = new Handler();
        mUpdate = () -> {
            if (mCurrentPage == mPageAdapter.getCount()) {
                mCurrentPage = 0;
            }
            mViewPager.setCurrentItem(mCurrentPage++, true);
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(mUpdate);
            }
        }, 5000, 5000);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mUpdate);
    }
}
