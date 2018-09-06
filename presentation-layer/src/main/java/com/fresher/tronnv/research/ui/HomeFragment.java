package com.fresher.tronnv.research.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.MainUseCases;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;
import com.fresher.tronnv.research.ui.adapter.MultiViewAdapter;
import com.fresher.tronnv.research.viewmodel.RecordChartViewModel;
import com.fresher.tronnv.research.viewmodel.TrackViewModel;

import java.util.ArrayList;
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
                    mMultiRecyclerViewAdapter.updateItems(new MainUseCases(getContext()).getDataTypes());
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
                    mMultiRecyclerViewAdapter.updateItems(new MainUseCases(getContext()).getDataTypes());
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
        final RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        SwipeRefreshLayout swipeRefreshLayout =rootView.findViewById(R.id.swipeRefreshLayout);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        mMultiRecyclerViewAdapter = new MultiViewAdapter(getContext());
        mMultiRecyclerViewAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mMultiRecyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(20,500);
        mMultiRecyclerViewAdapter.setPool(recyclerView.getRecycledViewPool());
        swipeRefreshLayout.setOnRefreshListener(() -> {
//            mMultiRecyclerViewAdapter.updateItems(new MainUseCases(getContext()).getDataTypes2());
            swipeRefreshLayout.setRefreshing(false);
        });
        return rootView;
    }
}
