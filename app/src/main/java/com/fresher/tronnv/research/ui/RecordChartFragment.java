package com.fresher.tronnv.research.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.data.source.RecordChart;
import com.fresher.tronnv.research.ui.adapter.HeaderPageAdapter;
import com.fresher.tronnv.research.ui.adapter.RecordChartAdapter;
import com.fresher.tronnv.research.viewmodel.RecordChartViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */

/**
 * A class PlayListFragment
 */
public class RecordChartFragment extends Fragment {
    //Interface to transfer data between two fragment

    private int currentPage = 0;
    private ViewPager viewPager;
    private boolean isLoading = true;
    List<RecordChart> recordChartList ;
    private RecordChartAdapter recordChartAdapter;
    public RecordChartFragment(){
        recordChartList = new ArrayList<>();
    }
    public boolean isLoading(){return isLoading;}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecordChartViewModel viewModel =
                ViewModelProviders.of(this).get(RecordChartViewModel.class);
            subscribeUI(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void subscribeUI(RecordChartViewModel viewModel){
        viewModel.getRecordChart().observe(this, new Observer<List<RecordChart>>() {
            @Override
            public void onChanged(@Nullable List<RecordChart> recordCharts) {
                if (recordCharts!= null){
                    recordChartAdapter.setRecordChartList(recordCharts);
                    recordChartAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
                else{
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_record_chart,container,false);

        viewPager = rootView.findViewById(R.id.view_pager);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(0,0,0,0);
        viewPager.setAdapter(new HeaderPageAdapter(getActivity()));
        viewPager.setCurrentItem(0, true);

        final RecyclerView listView = rootView.findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);
        recordChartAdapter = new RecordChartAdapter(getContext());
//        recordChartAdapter.setRecordChartList(recordChartList);
        //listView.setDivider(null);
        listView.setAdapter(recordChartAdapter);
        //recordChartAdapter.notifyDataSetChanged();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == recordChartAdapter.getItemCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);
        return rootView;
    }
}
