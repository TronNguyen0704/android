package com.fresher.tronnv.research.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.Constants;
import com.fresher.tronnv.research.R;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Objects;
import java.util.Random;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MultiViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<RecordChart> mRecordCharts;
    private List<Track> mTracks;
    private List<String> mDataList;
    private Context mContext;
    private List<String> mImages;
    private RecyclerView.RecycledViewPool mRecycledViewPool;

    private static SparseArray<View> sSpecials = new SparseArray<>();
    public MultiViewAdapter(Context context){
        mContext = context;
        mTracks = new ArrayList<>();
        mRecordCharts = new ArrayList<>();
        mImages = new ArrayList<>();
        mDataList = new ArrayList<>();
        mImages.add("https://zmp3-photo.zadn.vn/covers/e/9/e92910699de0aeee9f1587e0425d8a7c_1514894974.png");
        mImages.add("https://zmp3-photo.zadn.vn/covers/c/5/c5360ad3d2e28b5bb5f0d0930a6a6a6f_1499827454.jpg");
        mImages.add("https://zmp3-photo.zadn.vn/covers/9/5/95488ad8d45bd69ef83e9403c4114375_1499827707.jpg");
        mImages.add("https://zmp3-photo.zadn.vn/covers/7/0/702ed1b745f1b326f4fc07e8b60afea4_1499828300.jpg");
        mImages.add("https://zmp3-photo.zadn.vn/cover/5/5/5/e/555e1711704ed04400cce99ffbccc8b1.jpg");
        mImages.add("https://zmp3-photo.zadn.vn/covers/d/1/d1c2738deec7efd1942a3027a1c436b0_1499828277.jpg");
        mRecycledViewPool = new RecyclerView.RecycledViewPool();
//        mRecycledViewPool.setMaxRecycledViews(0,0);
//        mRecycledViewPool.setMaxRecycledViews(1,1);
//        mRecycledViewPool.setMaxRecycledViews(2,1);
//        mRecycledViewPool.setMaxRecycledViews(3,0);
//        mRecycledViewPool.setMaxRecycledViews(4,1);
//        mRecycledViewPool.setMaxRecycledViews(5,1);
//        mRecycledViewPool.setMaxRecycledViews(6,6);
    }
    public void setmRecycledViewPool(RecyclerView.RecycledViewPool r){
        this.mRecycledViewPool = r;
    }
    public SparseArray<View> getSpecials() {
        return sSpecials;
    }
    public RecyclerView.RecycledViewPool getRecycledViewPool() {
        return mRecycledViewPool;
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
    @Override
    public int getItemViewType(int position) {
        if (position <=11)
            return position;
        else if(position < 20)
            return 12;
        else if(position < 25)
            return 13;
        else
            return 14;
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull BaseViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: {
                Log.e("Debug", "OnCreateViewHolder");
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
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 4:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.grid_view_layout, parent, false);
                return new GridViewHolder(view);
            }
            case 5:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.recent_layout, parent, false);
                return new RecentViewHolder(view);
            }
            case 6:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.grid_view_layout, parent, false);
                return new GridViewHolder(view);
            }
            case 7:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 8:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.grid_view_layout, parent, false);
                return new GridViewHolder(view);
            }
            case 9:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.recent_layout, parent, false);
                return new RecentViewHolder(view);
            }
            case 10:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.grid_view_layout, parent, false);
                return new GridViewHolder(view);
            }
            case 11:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.rank_layout, parent, false);
                return new RecyclerViewHolder(view);
            }
            case 12:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 13:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.rank_layout, parent, false);
                return new RecyclerViewHolder(view);
            }
            case 14:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.grid_view_layout, parent, false);
                return new GridViewHolder(view);
            }
            default:{
                return null;
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
                onBindImageViewHolder(holder,true);
                holder.bindTo(position);
                break;
            }
            case 4: {
                onBindGridViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 5: {
                onBindRecentViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 6: {
                onBindGridViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 7: {
                onBindImageViewHolder(holder,true);
                holder.bindTo(position);
                break;
            }
            case 8: {
                onBindGridViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 9: {
                onBindRecentViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 10: {
                onBindGridViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 11: {
                onBindRecyclerViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 12: {
                onBindImageViewHolder(holder,true);
                holder.bindTo(position);
                break;
            }
            case 14: {
                onBindGridViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 13: {
                onBindRecyclerViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            default:{
                break;
            }
        }

    }
/*
 */
//    @Override
//    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
//        if(payloads.isEmpty()) {// if empty, do full binding
//            if(!isCreated) {
//                onBindViewHolder(holder, position);
//            }
//            if(position == mDataList.size() - 1 && !isCreated){
//                isCreated = true;
//            }
//            return;
//        }
//        Bundle bundle = (Bundle) payloads.get(0);
//        if(bundle.getString("POSITION_CHANGE").endsWith("1111")){
//            if(position > 5) {
//                onBindImageViewHolder(holder, true);
//            }
//        }
//    }


    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        if(mDataList != null){
            return mDataList.size();
        }
        return 0;
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        if(mRecycledViewPool!= null && mRecycledViewPool.getRecycledViewCount(0) >= 0){
            Log.e("View 0",String.valueOf(mRecycledViewPool.getRecycledViewCount(0)) );
            Log.e("View 1",String.valueOf(mRecycledViewPool.getRecycledViewCount(1)) );
            Log.e("View 2",String.valueOf(mRecycledViewPool.getRecycledViewCount(2)) );
            Log.e("View 3",String.valueOf(mRecycledViewPool.getRecycledViewCount(3)) );
            Log.e("View 4",String.valueOf(mRecycledViewPool.getRecycledViewCount(4)) );
            Log.e("View 5",String.valueOf(mRecycledViewPool.getRecycledViewCount(5)) );
        }
    }

    private void onBindPageViewHolder(BaseViewHolder holder){
        PageViewHolder pageViewHolder = (PageViewHolder) holder;
        ViewPager mViewPager = pageViewHolder.viewPager;
        mViewPager.setPadding(0, 0, 0, 0);
//        PageAdapter pageAdapter = new PageAdapter(sFragmentManager);
//        pageAdapter.setTracks(mTracks);
        mViewPager.setOffscreenPageLimit(4);
//        mViewPager.setAdapter(pageAdapter);
        HeaderPageAdapter headerPageAdapter = new HeaderPageAdapter((Activity)mContext);
        headerPageAdapter.setTrackList(mTracks);
        mViewPager.setAdapter(headerPageAdapter);
        mViewPager.setPageTransformer(false, (page, position) -> {
            int pageWidth = page.getWidth();
            float pageWidthTimesPosition = pageWidth * position;
            float absPosition = Math.abs(position);
            if (position <= -1.0f || position >= 1.0f) {
            } else if (position == 0.0f) {
            } else {
                View title = page.findViewById(R.id.tv_title);
                title.setTranslationY(-pageWidthTimesPosition / 2f);
                title.setAlpha(1.0f - absPosition);
                View description = page.findViewById(R.id.tv_description);
                description.setTranslationY(-pageWidthTimesPosition / 2f);
                description.setAlpha(1.0f - absPosition);
                View imgPlay = page.findViewById(R.id.img_play);
                imgPlay.setTranslationY(-pageWidthTimesPosition / 2f);
                imgPlay.setAlpha(1.0f - absPosition);
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
        mViewPager.setCurrentItem(new Random().nextInt(5), true);
    }
    private void onBindRecyclerViewHolder(BaseViewHolder holder){
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setSmoothScrollbarEnabled(true);
        recyclerViewHolder.recyclerView.setLayoutManager(llm);
        RecordChartAdapter mRecordChartAdapter = new RecordChartAdapter(mContext);
        mRecordChartAdapter.setRecordChartList(mRecordCharts.subList(0,5));
//        recyclerViewHolder.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayout.VERTICAL));
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
    private void onBindImageViewHolder(BaseViewHolder holder, boolean flag){
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        imageViewHolder.imageView.setOnClickListener(v -> {
            this.notifyItemChanged(3);
        });
        Glide.with(mContext)
                .load((!flag) ? imageViewHolder.images[0] : imageViewHolder.images[new Random().nextInt(5)])
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(imageViewHolder.imageView);
    }
    private void onBindGridViewHolder(BaseViewHolder holder){
        GridViewHolder gridViewHolder = (GridViewHolder) holder;
        GridAdapter gridAdapter = new GridAdapter(mContext);
        gridAdapter.setImages(mImages);
        gridViewHolder.gridView.setAdapter(gridAdapter);
    }
    final class ImageViewHolder extends BaseViewHolder {
        ImageView imageView;
        String [] images = new String[]{"https://zmp3-photo.zadn.vn/banner/d/a/1/8/da18143f5d86dfb3d127f0982e93b44c.jpg"
                ,"https://zmp3-photo.zadn.vn/banner/9/d/6/e/9d6e3b31a032d6bcade76fa840d1741b.jpg"
                ,"https://zmp3-photo.zadn.vn/banner/1/2/3/6/123664224c66c104ae292be5788c7682.jpg"
                ,"https://zmp3-photo.zadn.vn/banner/c/5/9/1/c5910f078e09e724916aa12bb7761018.jpg"
                ,"https://zmp3-photo.zadn.vn/banner/4/a/d/4/4ad435d507cb7167c9f0921837910345.jpg"
                ,"https://zmp3-photo.zadn.vn/banner/3/5/1/f/351f2d577395e79f36b3dc42616dcfc7.jpg"};
        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_avatar);
        }
        @Override
        void bindTo(int position){
        }
    }
    final class  RecentViewHolder extends BaseViewHolder {
        RecyclerView recyclerView;

        RecentViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
//            recyclerView.setItemViewCacheSize(9);
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
//            recyclerView.setItemViewCacheSize(5);
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
            sSpecials.put(position,itemView);
        }
    }
    final class  GridViewHolder extends BaseViewHolder {
        GridView gridView;
        GridViewHolder(View itemView) {
            super(itemView);
            gridView = itemView.findViewById(R.id.gv_week_chart);
            gridView.setNumColumns(2);
        }
        @Override
        void bindTo(int position){

        }
    }
    public void updateItems(List<String> newItems) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AwesomeCallback(newItems, mDataList));
        diffResult.dispatchUpdatesTo(this);

        mDataList.clear();
        mDataList.addAll(newItems);

    }
    static class AwesomeCallback extends DiffUtil.Callback {

        private List<String> oldList;
        private List<String> newList;

        public AwesomeCallback(List<String> oldList, List<String> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return true;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            boolean s;
            s = oldList.get(oldItemPosition).endsWith(newList.get(newItemPosition));
            return s;

        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            String oldData = oldList.get(oldItemPosition);
            String newData = newList.get(newItemPosition);

            Bundle payload = new Bundle();
            if (!(oldData.equals(newData))) {
                payload.putString("POSITION_CHANGE", newData);
            }

            if (payload.isEmpty()) {
                return null; // do full binding
            } else {
                return payload;
            }
        }
    }
}
