package com.fresher.tronnv.research.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import com.fresher.tronnv.android_models.ThirdBody;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.ui.adapter.RecyclerHolder;
import com.fresher.tronnv.research.ui.adapter.RecyclerItem;

public class ThirdBodyItem extends RecyclerItem {
    public static final int THRIDBODY_ITEM_RES_ID = R.layout.thirdbody_layout;
    private ThirdBody mThirdBody;
    public ThirdBodyItem(ThirdBody thirdBody){
        super();
        this.mThirdBody = thirdBody;
    }
    @Override
    public void updateView(RecyclerHolder parentHolder, int position) {
        Holder holder = (Holder) parentHolder;
        Utils.asyncSetText(holder.mTextDetail,mThirdBody.getTitle());
        Utils.asyncSetText(holder.mTextAuthor,mThirdBody.getAuthor());
        Utils.asyncSetText(holder.mTextViews,mThirdBody.getViews()+ " views");
//        holder.mTextDetail.setText(mThirdBody.getTitle());
//        holder.mTextAuthor.setText(mThirdBody.getAuthor());
//        holder.mTextViews.setText(mThirdBody.getViews() + " views");

    }
    @Override
    public void viewRecycled(RecyclerHolder parentHolder, int position) {
        super.viewRecycled(parentHolder, position);

        Holder holder = (Holder) parentHolder;
    }
    @Override
    public int getViewResId() {
        return THRIDBODY_ITEM_RES_ID;
    }
    public static class Holder extends RecyclerHolder {


        private TextView mTextDetail;
        private TextView mTextAuthor;
        private TextView mTextViews;

        public Holder(View itemView) {
            super(itemView);
            mTextDetail = itemView.findViewById(R.id.tv_detail);
            mTextAuthor = itemView.findViewById(R.id.tv_author);
            mTextViews = itemView.findViewById(R.id.tv_views);
        }
    }
}
