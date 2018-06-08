package com.fresher.tronnv.research.ui;


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

import com.fresher.tronnv.models.RecordChart;
import com.fresher.tronnv.models.Track;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;
import com.fresher.tronnv.research.ui.adapter.HeaderPageAdapter;
import com.fresher.tronnv.research.ui.adapter.RecordChartAdapter;

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
public class HomeFragment extends Fragment {
    //Interface to transfer data between two fragment

    public interface Loadfinish{
        void onFinish();
    }

    private int currentPage = 0;
    private ViewPager viewPager;
    private boolean isLoading = true;
    private boolean isLoadingPagerView = true;
    List<RecordChart> recordChartList ;
    List<Track> tracks ;
    private RecordChartAdapter recordChartAdapter;
    private HeaderPageAdapter headerPageAdapter;
    private Context context;
    private ApplicationPresenter applicationPresenter;
    public HomeFragment(){
        recordChartList = new ArrayList<>();
        tracks = new ArrayList<>();
        applicationPresenter = new ApplicationPresenterImpl();
        applicationPresenter.loadTrackData();
        applicationPresenter.loadRecordChartData();
    }
    public boolean isLoading(){return isLoading || isLoadingPagerView;}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Loadfinish){
            this.context = context;
        }
    }
    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_record_chart,container,false);

        viewPager = rootView.findViewById(R.id.view_pager);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(0,0,0,0);
        headerPageAdapter = new HeaderPageAdapter(getActivity());
        headerPageAdapter.setTrackList(applicationPresenter.getTracks());
        viewPager.setAdapter(headerPageAdapter);
        viewPager.setCurrentItem(0, true);
        final RecyclerView listView = rootView.findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);
        recordChartAdapter = new RecordChartAdapter(getContext());
        recordChartAdapter.setRecordChartList(applicationPresenter.getRecordCharts());
        if(isLoading()){
            ((Loadfinish)context).onFinish();
        }
        //listView.setDivider(null);
        listView.setAdapter(recordChartAdapter);
        recordChartAdapter.notifyDataSetChanged();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == headerPageAdapter.getCount()) {
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