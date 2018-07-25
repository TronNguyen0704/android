package com.fresher.tronnv.research.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
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
    private static SparseArray<View> sSparseArray;
    public MultiViewAdapter(Context context){
        mContext = context;
        mTracks = new ArrayList<>();
        mRecordCharts = new ArrayList<>();
        if(sSparseArray == null)
            sSparseArray = new SparseArray<>();
        else
            sSparseArray.clear();
    }
    public static SparseArray<View> getSparseArray() {
        return sSparseArray;
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
            default: return null;
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
        }

    }
    @Override
    public int getItemCount() {
        return mCount;
    }
/*
Đặt số trang cần được giữ lại ở hai bên của trang hiện tại trong cấu trúc phân cấp chế độ xem ở trạng thái không hoạt động.
Các trang vượt quá giới hạn này sẽ được tạo lại từ bộ điều hợp khi cần.
Điều này được cung cấp dưới dạng tối ưu hóa. Nếu bạn biết trước số lượng trang bạn sẽ cần hỗ trợ hoặc có cơ chế tải chậm ở vị trí trên các trang của mình,
việc tinh chỉnh cài đặt này có thể có lợi ích trong cảm giác mượt mà của hoạt ảnh và tương tác trên phân trang.
Nếu bạn có một số lượng nhỏ các trang (3-4) mà bạn có thể tiếp tục hoạt động cùng một lúc,
ít thời gian hơn sẽ được chi tiêu trong bố cục cho các chế độ xem chế độ xem mới được tạo thành các trang người dùng qua lại.
Bạn nên giữ giới hạn này thấp, đặc biệt nếu các trang của bạn có bố cục phức tạp. Cài đặt này mặc định là 1.
Thông số:
giới hạn - Số lượng trang sẽ được giữ kín ở trạng thái không hoạt động.
 */
    private void onBindPageViewHolder(BaseViewHolder holder){
        PageViewHolder pageViewHolder = (PageViewHolder) holder;
        ViewPager mViewPager = pageViewHolder.viewPager;
        mViewPager.setPadding(0, 0, 0, 0);
        HeaderPageAdapter mHeaderPageAdapter = new HeaderPageAdapter((Activity) mContext);
        mHeaderPageAdapter.setTrackList(mTracks);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mHeaderPageAdapter);
        mViewPager.setPageTransformer(false, (page, position) -> {
            int pageWidth = page.getWidth();
            float pageWidthTimesPosition = pageWidth * position;
            float absPosition = Math.abs(position);
            if (position <= -1.0f || position >= 1.0f) {
            } else if (position == 0.0f) {
            } else {
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
/*
Đặt số lần xem màn hình ngoài để giữ lại trước khi thêm chúng vào hồ bơi chế độ xem được tái chế có khả năng chia sẻ.
Bộ nhớ cache của chế độ xem ngoại tuyến vẫn nhận thức được những thay đổi trong bộ điều hợp kèm theo,
cho phép Trình quản lý bố cục sử dụng lại các chế độ xem đó chưa được sửa đổi mà không cần phải quay lại bộ điều hợp để kết nối lại chúng.
Thông số:
size - Số lượt xem cho bộ nhớ cache tắt màn hình trước khi đưa chúng trở lại chế độ xem chung được tái chế
 */
    final class  RecentViewHolder extends BaseViewHolder {
        RecyclerView recyclerView;

        RecentViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.setItemViewCacheSize(9);
        }
        @Override
        void bindTo(int position){
            sSparseArray.put(position,itemView);
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
            sSparseArray.put(position,itemView);
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
            sSparseArray.put(position,itemView);
        }
    }
}
