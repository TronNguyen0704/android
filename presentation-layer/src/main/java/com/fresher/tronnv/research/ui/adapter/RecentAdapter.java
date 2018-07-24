package com.fresher.tronnv.research.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.android_models.RecordChart;
import com.fresher.tronnv.android_models.Track;
import com.fresher.tronnv.research.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecyclerViewHolder> {
    private List<RecordChart> mRecordCharts;
    private Context mContext;
    private static HashMap<String,RecyclerViewHolder> sViewCache;
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
    public RecentAdapter(Context context) {
        this.mContext = context;
        mRecordCharts = new ArrayList<>();
        if(sViewCache == null){
            sViewCache = new HashMap<>();
        }
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(parent.getChildCount()==0){
            sViewCache.clear();
            if(sViewCache == null){
                sViewCache = new HashMap<>();
            }
        }
        View view = inflater.inflate(R.layout.recent_item, parent, false);
        return new RecyclerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
           if(!sViewCache.containsKey(mRecordCharts.get(position).getName())) {
               holder.bindList(mRecordCharts,mContext,position);
               sViewCache.put(mRecordCharts.get(position).getName(), holder);
           }
    }

    @Override
    public int getItemCount() {
        if (mRecordCharts != null)
            return mRecordCharts.size();
        return 0;
    }

     static class  RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_avatar;
        TextView txtSongName;
        TextView txtAuthor;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.img_avatar);
            txtSongName = itemView.findViewById(R.id.tv_name);
            txtAuthor = itemView.findViewById(R.id.tv_author);
        }
        void bindList(List<RecordChart> recordCharts, Context context,int position){
            txtSongName.setText(recordCharts.get(position).getName() + "");
            txtAuthor.setText(recordCharts.get(position).getAuthor() + "");
            Glide.with(context)
                    .load(recordCharts.get(position).getAvatar())
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(iv_avatar);
        }
    }
}
