package com.fresher.tronnv.research.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultiViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<RecordChart> mRecordCharts;
    private List<Track> mTracks;
    private Context mContext;
    private int mCount = 0;
    private FragmentManager mFragmentManager;

    public MultiViewAdapter(Context context){
        mContext = context;
        mTracks = new ArrayList<>();
        mRecordCharts = new ArrayList<>();
    }
    public void setRecordChartList(List<RecordChart> recordChart) {
        if (this.mRecordCharts == null) {
            this.mRecordCharts.addAll(recordChart);
            notifyItemRangeInserted(0, recordChart.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mRecordCharts.size();
                }

                @Override
                public int getNewListSize() {
                    return recordChart.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mRecordCharts.get(oldItemPosition).getId() ==
                            recordChart.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    RecordChart newRecorChart = recordChart.get(newItemPosition);
                    RecordChart oldRecordChart = mRecordCharts.get(oldItemPosition);
                    return newRecorChart.getId() == oldRecordChart.getId()
                            && Objects.equals(newRecorChart.getName(), oldRecordChart.getName())
                            && Objects.equals(newRecorChart.getName(), oldRecordChart.getName())
                            && newRecorChart.getAuthor() == oldRecordChart.getAuthor();
                }
            });
            mRecordCharts.addAll(recordChart);
            result.dispatchUpdatesTo(this);
        }
    }
    public void setTracks(List<Track> mTracks) {
        this.mTracks = mTracks;
    }
    public void setCount(int count){
        mCount = count;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.slider_layout, parent, false);
                return new PageViewHolder(view);
            }
            case 1:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.rank_layout, parent, false);
                return new RecyclerViewHolder(view);
            }
            case 2:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.recent_layout, parent, false);
                return new RecentViewHolder(view);
            }
            case 3:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.rank_layout, parent, false);
                return new RecyclerViewHolder(view);
            }
            case 4:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.recent_layout, parent, false);
                return new RecentViewHolder(view);
            }
            default:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.recent_layout, parent, false);
                return new RecyclerViewHolder(view);
            }
        }
    }
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0: {
                onBindPageViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 1: {
                onBindRecyclerViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 2: {
                onBindRecentViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 3: {
                onBindRecyclerViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 4: {
                onBindRecentViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            default:{
                onBindRecyclerViewHolder(holder);
                holder.bindTo(position);
                break;
            }
        }

    }
    @Override
    public int getItemCount() {
        return mCount;
    }

    private void onBindPageViewHolder(BaseViewHolder holder){
        PageViewHolder pageViewHolder = (PageViewHolder) holder;
        ViewPager mViewPager = pageViewHolder.viewPager;
        mViewPager.setPadding(0, 0, 0, 0);
        PageAdapter pageAdapter = new PageAdapter(mFragmentManager);
        pageAdapter.setTracks(mTracks);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setPageTransformer(false, (page, position) -> {
            int pageWidth = page.getWidth();
            float pageWidthTimesPosition = pageWidth * position;
            float absPosition = Math.abs(position);
            if (position <= -1.0f || position >= 1.0f) {
            } else if (position == 0.0f) {
            } else {
                View title = page.findViewById(R.id.tv_title);
                title.setAlpha(1.0f - absPosition);
                title.setTranslationY(-pageWidthTimesPosition / 2f);
                View description = page.findViewById(R.id.tv_description);
                description.setTranslationY(-pageWidthTimesPosition / 2f);
                description.setAlpha(1.0f - absPosition);
                View imgPlay = page.findViewById(R.id.img_play);
                imgPlay.setAlpha(1.0f - absPosition);
                imgPlay.setTranslationX(-pageWidthTimesPosition * 1.5f);
                View avatar = page.findViewById(R.id.img_avatar);
                avatar.setScaleX(1.0f - absPosition);
                avatar.setScaleY(1.0f - absPosition);
                avatar.setTranslationX(-pageWidthTimesPosition);
                if (position < 0) {
                    // Create your out animation here
                    avatar.setTranslationX(pageWidthTimesPosition * 0.0f);
                    avatar.setScaleX(1.0f);
                    avatar.setScaleY(1.0f);
                } else {
                    // Create your in animation here
                    avatar.setScaleX(1.0f + absPosition);
                    avatar.setScaleY(1.0f + absPosition);
                }
            }
        });
        mViewPager.setCurrentItem(0, true);
    }
    private void onBindRecyclerViewHolder(BaseViewHolder holder){
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setSmoothScrollbarEnabled(true);
        recyclerViewHolder.recyclerView.setLayoutManager(llm);
        RecordChartAdapter mRecordChartAdapter = new RecordChartAdapter(mContext);
        mRecordChartAdapter.setRecordChartList(mRecordCharts.subList(0,5));
        recyclerViewHolder.recyclerView.setAdapter(mRecordChartAdapter);
    }
    private void onBindRecentViewHolder(BaseViewHolder holder){
        RecentViewHolder recyclerViewHolder = (RecentViewHolder) holder;
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setSmoothScrollbarEnabled(true);
        recyclerViewHolder.recyclerView.setLayoutManager(llm);
        RecentAdapter recentAdapter = new RecentAdapter(mContext);
        recentAdapter.setRecordChartList(mRecordCharts);
        recyclerViewHolder.recyclerView.setAdapter(recentAdapter);
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    final class  RecentViewHolder extends BaseViewHolder {
        RecyclerView recyclerView;

        RecentViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.setItemViewCacheSize(9);
        }
        @Override
        void bindTo(int position){

        }
    }
    final class  RecyclerViewHolder extends BaseViewHolder {
        RecyclerView recyclerView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.setItemViewCacheSize(5);
        }
        @Override
        void bindTo(int position){

        }
    }
    final class  PageViewHolder extends BaseViewHolder {
        ViewPager viewPager;
        PageViewHolder(View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.view_pager);
        }
        @Override
        void bindTo(int position){

        }
    }
}
