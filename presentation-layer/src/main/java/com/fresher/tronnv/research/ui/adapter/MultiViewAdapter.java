package com.fresher.tronnv.research.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.android_models.DataType;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.GlideHelper;
import com.fresher.tronnv.research.MainUseCases;
import com.fresher.tronnv.research.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
public class MultiViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<RecordChart> mRecordCharts;
    private List<Track> mTracks;
    private List<DataType> mDataList;
    private Context mContext;
    private boolean mIsCreated;
    private SparseArray<BaseViewHolder> sSpecials ;
    private RecyclerView.RecycledViewPool mPool;
    private int mCount = 0;

    public MultiViewAdapter(Context context){
        mContext = context;
        mTracks = new ArrayList<>();
        mRecordCharts = new ArrayList<>();
        mDataList = new ArrayList<>();
        sSpecials = new SparseArray<>();
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
        else if(position < 21)
            return 12;
        else if(position < 66)
            return 13;
        else if(position < 70)
            return 14;
        if(position == 0)
            return position;
        return 20;
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        for(int i = 0; i < sSpecials.size(); i++){
//            BaseViewHolder baseViewHolder = sSpecials.get(sSpecials.keyAt(i));
//            if( baseViewHolder != null && baseViewHolder.getItemViewType() == viewType){
//                sSpecials.remove(sSpecials.keyAt(i));
//                return baseViewHolder;
//            }
//        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 0: {
                Log.e("Debug", "OnCreateViewHolder");
                View view = inflater.inflate(R.layout.slider_layout, parent, false);
                return new PageViewHolder(view);
            }
            case 1:{
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 2:{
                View view = inflater.inflate(R.layout.recent_layout, parent, false);
                return new RecentViewHolder(view);
            }
            case 3:{
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 4:{
                View view = inflater.inflate(R.layout.recent_layout, parent, false);
                return new RecentViewHolder(view);
            }
            case 5:{
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 6:{
                View view = inflater.inflate(R.layout.grid_view_layout, parent, false);
                return new GridViewHolder(view);
            }
            case 7:{
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 8:{
                View view = inflater.inflate(R.layout.recent_layout, parent, false);
                return new RecentViewHolder(view);
            }
            case 9:{
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 10:{
                View view = inflater.inflate(R.layout.grid_view_layout, parent, false);
                return new GridViewHolder(view);
            }
            case 11:{
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 12:{
                View view = inflater.inflate(R.layout.rank_item, parent, false);
                return new ItemViewHolder(view);
            }
            case 13:{
                View view = inflater.inflate(R.layout.basic_view_layout, parent, false);
                return new ImageViewHolder(view);
            }
            case 14:{
                View view = inflater.inflate(R.layout.grid_view_layout, parent, false);
                return new GridViewHolder(view);
            }
            case 20:{
                View view = inflater.inflate(R.layout.newfeed_layout, parent, false);
                return new ConstraintViewHolder(view);
            }
            default:{
                View view = inflater.inflate(R.layout.grid_view_layout, parent, false);
                return new GridViewHolder(view);
            }
        }
    }
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if(mPool!= null)
            Log.e("onBindViewHolder",String.valueOf(mPool.getRecycledViewCount(20)));
        onBindBaseViewHolder(holder,position);

    }
    private void onBindBaseViewHolder(BaseViewHolder holder,int position){
        switch (holder.getItemViewType()) {
            case 0: {
                onBindPageViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 1: {
                onBindImageViewHolder(holder,true);
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
                onBindRecentViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 5: {
                onBindImageViewHolder(holder,true);
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
                onBindRecentViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 9: {
                onBindImageViewHolder(holder,true);
                holder.bindTo(position);
                break;
            }
            case 10: {
                onBindGridViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 11: {
                onBindImageViewHolder(holder,true);
                holder.bindTo(position);
                break;
            }
            case 12: {
                onBindItemViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 13: {
                onBindImageViewHolder(holder,true);
                holder.bindTo(position);
                break;
            }
            case 14: {
                onBindGridViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            case 20: {
                onBindConstraintViewHolder(holder);
                holder.bindTo(position);
                break;
            }
            default:{
                break;
            }
        }
    }
//    @Override
//    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
//        if(payloads.isEmpty()) {// if empty, do full binding
//            if(!mIsCreated && position == mDataList.size() - 1){
//                mIsCreated = true;
//            }
//            if(!mIsCreated)
//                onBindViewHolder(holder, position);
////            return;
//        }
//        Bundle bundle = (Bundle) payloads.get(0);
//        Log.e(getClass().getSimpleName(),"POSITION_CHANGE" + bundle.getInt("POSITION_CHANGE"));
//    }


    @Override
    public int getItemCount() {
        if(mDataList != null){
            return mDataList.size();
        }
        return 0;
    }

    private void onBindPageViewHolder(BaseViewHolder holder){
        PageViewHolder pageViewHolder = (PageViewHolder) holder;
    }
    private void onBindRecyclerViewHolder(BaseViewHolder holder){
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewHolder.recyclerView.setLayoutManager(llm);
        RecordChartAdapter mRecordChartAdapter = new RecordChartAdapter(mContext);
        mRecordChartAdapter.setRecordChartList(mRecordCharts.subList(0,5));
        recyclerViewHolder.recyclerView.setAdapter(mRecordChartAdapter);
        recyclerViewHolder.recyclerView.setHasFixedSize(true);
        recyclerViewHolder.recyclerView.setNestedScrollingEnabled(false);
    }
    private void onBindRecentViewHolder(BaseViewHolder holder){
        RecentViewHolder recyclerViewHolder = (RecentViewHolder) holder;
    }
    private void onBindImageViewHolder(BaseViewHolder holder, boolean flag){
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
//        Glide.with(mContext)
//                .load((!flag) ? imageViewHolder.images[0] : imageViewHolder.images[new Random().nextInt(5)])
//                .apply(RequestOptions.bitmapTransform(
//                        new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
//                .into(imageViewHolder.imageView);
        GlideHelper.requestBuilderImage((!flag) ? imageViewHolder.images[0] : imageViewHolder.images[new Random().nextInt(5)],
                imageViewHolder.imageView).into(imageViewHolder.imageView);
    }
    private void onBindConstraintViewHolder(BaseViewHolder holder){
        ConstraintViewHolder constraintViewHolder = (ConstraintViewHolder)holder;
        constraintViewHolder.imageViewContent.setImageResource(R.drawable.oldmusic);
    }
    private void onBindItemViewHolder(BaseViewHolder holder){
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        int[] cols = new int[]{Color.BLUE, Color.GREEN, Color.RED, Color.WHITE};
        itemViewHolder.textViewRank.setText(String.valueOf(mRecordCharts.get(mCount).getRank()));
        itemViewHolder.textViewRank.setTextColor(cols[(mCount < 3) ? mCount : 3]);
        itemViewHolder.textViewName.setText(mRecordCharts.get(mCount).getName());
        itemViewHolder.textViewAuthor.setText(mRecordCharts.get(mCount).getAuthor());
//        Glide.with(mContext)
//                .load(mRecordCharts.get(mCount).getAvatar())
//                .apply(RequestOptions.bitmapTransform(
//                        new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
//                .into(itemViewHolder.imageView);
        GlideHelper.requestBuilderAvatar(mRecordCharts.get(mCount).getAvatar(),itemViewHolder.imageView).into(itemViewHolder.imageView);
        mCount = (mCount + 1) % 10;
    }
    private void onBindGridViewHolder(BaseViewHolder holder){
        GridViewHolder gridViewHolder = (GridViewHolder) holder;
    }

    public void setPool(RecyclerView.RecycledViewPool pool) {
        this.mPool = pool;
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
//        int viewType = holder.getItemViewType();
//        if(4 == mPool.getRecycledViewCount(viewType)){
//            sSpecials.put(holder.getAdapterPosition(),(BaseViewHolder) mPool.getRecycledView(viewType));
//        }
//        if(5 <= mPool.getRecycledViewCount(viewType)){
//            sSpecials.put(holder.getAdapterPosition(),holder);
//        }
        super.onViewRecycled(holder);
    }

    final class ItemViewHolder extends BaseViewHolder {
        ImageView imageView;
        TextView textViewAuthor;
        TextView textViewName;
        TextView textViewRank;
        ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_avatar);
            textViewAuthor = itemView.findViewById(R.id.tv_author);
            textViewName = itemView.findViewById(R.id.tv_name);
            textViewRank = itemView.findViewById(R.id.tv_rank);
        }
        @Override
        void bindTo(int position){
        }
    }
    final class ConstraintViewHolder extends BaseViewHolder {
        ImageView imageViewAdmin;
        ImageView imageViewContent;
        TextView textViewShare;
        TextView textViewTime;
        TextView textViewContent;
        TextView textViewDetail;
        ConstraintViewHolder(View itemView) {
            super(itemView);
            imageViewAdmin = itemView.findViewById(R.id.img_admin);
            imageViewContent = itemView.findViewById(R.id.img_content);
            textViewShare = itemView.findViewById(R.id.tv_share_content);
            textViewTime = itemView.findViewById(R.id.tv_share_time);
            textViewContent = itemView.findViewById(R.id.tv_content);
            textViewDetail = itemView.findViewById(R.id.tv_detail);
        }
        @Override
        void bindTo(int position){
        }
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
            imageView.setOnClickListener(v -> {
                Glide.with(mContext)
                        .load(images[new Random().nextInt(5)])
                        .apply(RequestOptions.bitmapTransform(
                                new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                        .into(imageView);
            });
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
            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(llm);
            RecentAdapter recentAdapter = new RecentAdapter(mContext);
            recentAdapter.setRecordChartList(mRecordCharts);
           recyclerView.setAdapter(recentAdapter);
           recyclerView.setHasFixedSize(true);
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
        ViewPager mViewPager;
        PageViewHolder(View itemView) {
            super(itemView);
            mViewPager = itemView.findViewById(R.id.view_pager);
            mViewPager.setPadding(0, 0, 0, 0);
            mViewPager.setOffscreenPageLimit(5);
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
            mViewPager.setCurrentItem(new Random().nextInt(5), true);
            mViewPager.setNestedScrollingEnabled(true);
        }
        @Override
        void bindTo(int position){
        }
    }
    final class  GridViewHolder extends BaseViewHolder {
        GridView gridView;
        GridViewHolder(View itemView) {
            super(itemView);
            gridView = itemView.findViewById(R.id.gv_week_chart);
            gridView.setNumColumns(2);
            GridAdapter gridAdapter = new GridAdapter(mContext);
            gridAdapter.setImages(new MainUseCases(null).getImages());
            gridView.setAdapter(gridAdapter);
        }
        @Override
        void bindTo(int position){

        }
    }
    public void updateItems(List<DataType> newItems) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AwesomeCallback(newItems, mDataList));
        diffResult.dispatchUpdatesTo(this);

        mDataList.clear();
        mDataList.addAll(newItems);

    }
    static class AwesomeCallback extends DiffUtil.Callback {

        private List<DataType> oldList;
        private List<DataType> newList;

        public AwesomeCallback(List<DataType> oldList, List<DataType> newList) {
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
            s = oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            return s;

        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            DataType oldData = oldList.get(oldItemPosition);
            DataType newData = newList.get(newItemPosition);

            Bundle payload = new Bundle();
            if (!(oldData.equals(newData))) {
                payload.putInt("POSITION_CHANGE", newData.getType());
            }

            if (payload.isEmpty()) {
                return null; // do full binding
            } else {
                return payload;
            }
        }
    }
}
