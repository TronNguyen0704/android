package com.fresher.tronnv.research.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.fresher.tronnv.android_models.DataType;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericAdapter<DataType> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<DataType> mDataList;
    public abstract BaseViewHolder setViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindData(BaseViewHolder holder, int pos);

    public GenericAdapter(Context context){
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = setViewHolder(parent,viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        onBindData(holder,position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void addItems( ArrayList<DataType> savedCardItemz){
        mDataList = savedCardItemz;
        this.notifyDataSetChanged();
    }

    public DataType getItem(int position){
        return mDataList.get(position);
    }
}
