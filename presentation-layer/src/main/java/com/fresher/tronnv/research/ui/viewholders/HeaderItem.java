package com.fresher.tronnv.research.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.android_models.Header;
import com.fresher.tronnv.research.GlideHelper;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.ui.adapter.RecyclerHolder;
import com.fresher.tronnv.research.ui.adapter.RecyclerItem;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class HeaderItem extends RecyclerItem {
    public static final int HEADER_ITEM_RES_ID = R.layout.header_layout;
    private Header mHeader;
    public HeaderItem(Header header){
        super();
        mHeader = header;
    }
    @Override
    public void updateView(RecyclerHolder parentHolder, int position) {
        Holder holder = (Holder) parentHolder;
//        Glide.with(holder.getContext())
//                .load(mHeader.getAvatar())
//                .apply(RequestOptions.bitmapTransform(
//                        new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
//                .into(holder.mAvatarView);
        GlideHelper.requestBuilderAvatar(mHeader.getAvatar(),holder.mAvatarView).into(holder.mAvatarView);
        Utils.asyncSetText(holder.mTextAdmin,mHeader.getAdminName());
        Utils.asyncSetText(holder.mTextShareType,"shared a " +mHeader.getShareType());
        Utils.asyncSetText(holder.mTextShareTime,mHeader.getShareTime()+ " hour ago");
//        holder.mTextAdmin.setText(mHeader.getAdminName());
//        holder.mTextShareType.setText("shared a " + mHeader.getShareType());
//        holder.mTextShareTime.setText(mHeader.getShareTime() + " hour ago");

    }
    @Override
    public void viewRecycled(RecyclerHolder parentHolder, int position) {
        super.viewRecycled(parentHolder, position);

        Holder holder = (Holder) parentHolder;
    }
    @Override
    public int getViewResId() {
        return HEADER_ITEM_RES_ID;
    }
    public static class Holder extends RecyclerHolder {
        private ImageView mAvatarView;
        private TextView mTextAdmin;
        private TextView mTextShareType;
        private TextView mTextShareTime;

        public Holder(View itemView) {
            super(itemView);
            mAvatarView = itemView.findViewById(R.id.img_admin);
            mTextAdmin = itemView.findViewById(R.id.tv_admin);
            mTextShareType = itemView.findViewById(R.id.tv_share_content);
            mTextShareTime = itemView.findViewById(R.id.tv_share_time);
        }
    }
}
