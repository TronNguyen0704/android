package com.fresher.tronnv.research.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fresher.tronnv.android_models.NewFeed;
import com.fresher.tronnv.research.GlideHelper;
import com.fresher.tronnv.research.R;

public class NewFeedItem extends RecyclerItem {
    static final int PRODUCT_ITEM_RES_ID = R.layout.newfeed_layout;
    private NewFeed mNewFeed;
    public NewFeedItem(NewFeed newFeed){
        super();
        mNewFeed = newFeed;
    }
    @Override
    public void updateView(RecyclerHolder parentHolder, int position) {
        Holder holder = (Holder) parentHolder;
        holder.mPhotoView.setImageResource(R.drawable.oldmusic);

    }
    @Override
    public void viewRecycled(RecyclerHolder parentHolder, int position) {
        super.viewRecycled(parentHolder, position);

        Holder holder = (Holder) parentHolder;
    }
    @Override
    public int getViewResId() {
        return PRODUCT_ITEM_RES_ID;
    }
    public static class Holder extends RecyclerHolder {

        private ImageView mPhotoView;
        private ImageView mAvatarView;
        private TextView mTextAdmin;
        private TextView mTextShare;
        private TextView mTextShareTime;
        private TextView mTextContent;
        private TextView mTextAuthor;
        private TextView mTextViews;
        private TextView mTextLikes;

        public Holder(View itemView) {
            super(itemView);
            mPhotoView = itemView.findViewById(R.id.img_content);
            mAvatarView = itemView.findViewById(R.id.img_admin);
            mTextAdmin = itemView.findViewById(R.id.tv_admin);
            mTextShare = itemView.findViewById(R.id.tv_share_content);
            mTextShareTime = itemView.findViewById(R.id.tv_share_time);
            mTextContent = itemView.findViewById(R.id.tv_content);
            mTextAuthor = itemView.findViewById(R.id.tv_detail);
            mTextViews = itemView.findViewById(R.id.tv_views);
            mTextLikes = itemView.findViewById(R.id.tv_likes);
        }
    }
}
