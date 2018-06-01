package com.fresher.tronnv.research.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.data.source.RecordChart;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;
import com.fresher.tronnv.research.viewmodel.RecordChartViewModel;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */

/**
 * A class PlayListFragment
 */
public class RecordChartFragment extends Fragment {
    //Interface to transfer data between two fragment

    List<RecordChart> recordChartList ;
    private RecordChartAdapter recordChartAdapter;
    public RecordChartFragment(){
        recordChartList = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecordChartViewModel viewModel =
                ViewModelProviders.of(this).get(RecordChartViewModel.class);
            subscribeUI(viewModel);
    }

    private void subscribeUI(RecordChartViewModel viewModel){
        viewModel.getRecordChart().observe(this, new Observer<List<RecordChart>>() {
            @Override
            public void onChanged(@Nullable List<RecordChart> recordCharts) {
                if (recordCharts!= null){
                    recordChartAdapter.setRecordChartList(recordCharts);
                    recordChartAdapter.notifyDataSetChanged();
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



        final RecyclerView listView = rootView.findViewById(R.id.list_item);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);
        recordChartAdapter = new RecordChartAdapter(getContext());
//        recordChartAdapter.setRecordChartList(recordChartList);
        //listView.setDivider(null);
        listView.setAdapter(recordChartAdapter);
        //recordChartAdapter.notifyDataSetChanged();

        return rootView;
    }
}
