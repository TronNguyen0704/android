package com.fresher.tronnv.research.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.Constants;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;
import com.fresher.tronnv.research.ui.adapter.MultiViewAdapter;
import com.fresher.tronnv.research.viewmodel.RecordChartViewModel;
import com.fresher.tronnv.research.viewmodel.TrackViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class HomeFragment extends Fragment {

    public interface Loadfinish {
        void onFinish();
    }
    private boolean mIsLoading = true;
    private boolean mIsLoadingPagerView = true;
    List<RecordChart> mRecordChartList;
    List<Track> mTracks;
    private MultiViewAdapter mMultiRecyclerViewAdapter;
    private Loadfinish mContext;
    private ApplicationPresenter mApplicationPresenter;
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
        viewModel.getRecordChart().observe(this, recordCharts -> {
            if (recordCharts != null) {
//                    mRecordChartAdapter.setRecordChartList(recordCharts);
//                    mRecordChartAdapter.notifyDataSetChanged();
                mMultiRecyclerViewAdapter.setRecordChartList(recordCharts);
                mMultiRecyclerViewAdapter.notifyDataSetChanged();
                mIsLoading = false;
                if (isLoading()) {
                    mMultiRecyclerViewAdapter.updateItems(Constants.generatorIntegers());
                    mContext.onFinish();
                }
            } else {
                mIsLoading = true;
            }
        });
    }

    private void subscribeUI(TrackViewModel viewModel) {
        viewModel.getTracks().observe(this, tracks -> {
            if (tracks != null) {
                mMultiRecyclerViewAdapter.setTracks(tracks);
                mMultiRecyclerViewAdapter.notifyDataSetChanged();
                mIsLoadingPagerView = false;
                if (isLoading()) {
                    mMultiRecyclerViewAdapter.updateItems(Constants.generatorIntegers());
                   mContext.onFinish();
                }
            } else {
                mIsLoadingPagerView = true;
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
//        mViewPager = rootView.findViewById(R.id.view_pager);
//        mViewPager.setClipToPadding(false);
//        mViewPager.setPadding(0, 0, 0, 0);
//        //mHeaderPageAdapter = new HeaderPageAdapter(getActivity());
//        //mPageAdapter = new PageAdapter(getFragmentManager());
//        //mViewPager.setAdapter(mHeaderPageAdapter);
//        mViewPager.setAdapter(mPageAdapter);
//        mViewPager.setCurrentItem(0, true);
//        mViewPager.setPageTransformer(false, (page, position) -> {
//            int pageWidth = page.getWidth();
//            float pageWidthTimesPosition = pageWidth * position;
//            float absPosition = Math.abs(position);
//            if (position <= -1.0f || position >= 1.0f) {
//
//
//            } else if (position == 0.0f) {
//
//
//            } else {
//                View title = page.findViewById(R.id.tv_title);
//                title.setAlpha(1.0f - absPosition);
//                View description = page.findViewById(R.id.tv_description);
//                description.setTranslationY(-pageWidthTimesPosition / 2f);
//                description.setAlpha(1.0f - absPosition);
//                View computer = page.findViewById(R.id.img_play);
//                computer.setAlpha(1.0f - absPosition);
//                computer.setTranslationX(-pageWidthTimesPosition * 1.5f);
//                View avatar = page.findViewById(R.id.img_avatar);
//                avatar.setScaleX(1.0f - absPosition);
//                avatar.setScaleY(1.0f - absPosition);
//                avatar.setTranslationX(-pageWidthTimesPosition);
//                if (position < 0) {
//                    // Create your out animation here
//                    avatar.setTranslationX(pageWidthTimesPosition*0.0f);
//                    avatar.setScaleX(1.0f);
//                    avatar.setScaleY(1.0f);
//                } else {
//                    // Create your in animation here
//                    avatar.setScaleX(1.0f + absPosition);
//                    avatar.setScaleY(1.0f + absPosition);
//                }
//            }
//
//        });
        final RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        SwipeRefreshLayout swipeRefreshLayout =rootView.findViewById(R.id.swipeRefreshLayout);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //llm.setExtraLayoutSpace(Utils.getScreenHight(getActivity()));
//        llm.setSmoothScrollbarEnabled(true);
//        llm.setItemPrefetchEnabled(false);
//        llm.scrollToPosition(0);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setItemViewCacheSize(30);
//        recyclerView.setClipToPadding(true);
        mMultiRecyclerViewAdapter = new MultiViewAdapter(getContext());
        recyclerView.setAdapter(mMultiRecyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.scrollToPosition(30);
//        mMultiRecyclerViewAdapter.setmRecycledViewPool(recyclerView.getRecycledViewPool());
        recyclerView.setRecycledViewPool(mMultiRecyclerViewAdapter.getRecycledViewPool());
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mMultiRecyclerViewAdapter.updateItems(Constants.generatorIntegers2());
//            mMultiRecyclerViewAdapter.notifyItemRangeChanged(0,2);
//            mMultiRecyclerViewAdapter.notifyItemChanged(1);
            swipeRefreshLayout.setRefreshing(false);
        });
//        recyclerView.setViewCacheExtension(new RecyclerView.ViewCacheExtension() {
//            @Override
//            public View getViewForPositionAndType(RecyclerView.Recycler recycler,
//                                                  int position, int type) {
//                return type == 0 ? mMultiRecyclerViewAdapter.getSpecials().get(position) : null;
//            }
//        });
//        recyclerView.setViewCacheExtension(new RecyclerView.ViewCacheExtension() {
//            @Override
//            public View getViewForPositionAndType(RecyclerView.Recycler recycler, int position, int type) {
//
//                if (type >= 0 && type <=30) {
//                   View view =  mMultiRecyclerViewAdapter.getSparseArray().get(position);
//                   if(view == null) {return null;}
//                   recyclerView.getLayoutManager().addView(view);
//                   return view;
//                }
//                return null;
//            }
//        });
//        mMultiRecyclerViewAdapter.notifyDataSetChanged();
//        new Handler().post(() -> {
//            recyclerView.getRecycledViewPool()
//                    .setMaxRecycledViews(0, 1);
//            recyclerView.getRecycledViewPool()
//                    .setMaxRecycledViews(1, 1);
//            recyclerView.getRecycledViewPool()
//                    .setMaxRecycledViews(2, 1);
//            recyclerView.getRecycledViewPool()
//                    .setMaxRecycledViews(3, 1);
//        });
        // Auto start of viewpager
//        mHandler = new Handler();
//        mUpdate = () -> {
//            if (mCurrentPage == mPageAdapter.getCount()) {
//                mCurrentPage = 0;
//            }
//            mViewPager.setCurrentItem(mCurrentPage++, true);
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                mHandler.post(mUpdate);
//            }
//        }, 5000, 5000);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
//    public class PreCachingLayoutManager extends LinearLayoutManager {
//        private static final int DEFAULT_EXTRA_LAYOUT_SPACE = 600;
//        private int extraLayoutSpace = -1;
//        private Context context;
//
//        public PreCachingLayoutManager(Context context) {
//            super(context);
//            this.context = context;
//        }
//
//        public PreCachingLayoutManager(Context context, int extraLayoutSpace) {
//            super(context);
//            this.context = context;
//            this.extraLayoutSpace = extraLayoutSpace;
//        }
//
//        public PreCachingLayoutManager(Context context, int orientation, boolean reverseLayout) {
//            super(context, orientation, reverseLayout);
//            this.context = context;
//        }
//
//        public void setExtraLayoutSpace(int extraLayoutSpace) {
//            this.extraLayoutSpace = extraLayoutSpace;
//        }
//
//        @Override
//        protected int getExtraLayoutSpace(RecyclerView.State state) {
//            if (extraLayoutSpace > 0) {
//                return extraLayoutSpace;
//            }
//            return DEFAULT_EXTRA_LAYOUT_SPACE;
//        }
//    }
}
