package com.fresher.tronnv.research.ui.viewholders;

import android.content.Intent;
import android.graphics.PointF;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.android_models.PlayListBody;
import com.fresher.tronnv.research.GlideHelper;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.activities.DetailActivity;
import com.fresher.tronnv.research.ui.adapter.RecyclerHolder;
import com.fresher.tronnv.research.ui.adapter.RecyclerItem;


import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;
public class PlaylistBodyItem extends RecyclerItem {
    public static final int PLAYLIST_ITEM_RES_ID = R.layout.playlist_layout;
    private PlayListBody mPlayListBody;
    public PlaylistBodyItem(PlayListBody playListBody){
        super();
        this.mPlayListBody = playListBody;
    }
    @Override
    public void updateView(RecyclerHolder parentHolder, int position) {
        Holder holder = (Holder) parentHolder;
        Glide.with(holder.getContext())
                .load(mPlayListBody.getPhotoRes())
                .apply(RequestOptions.bitmapTransform(
                        new VignetteFilterTransformation(
                                new PointF(0.7f, 0.7f),
                                new float[]{0f, 0f, 0f}, 0.1f, 1f)))
                .into(holder.mPhotoView);
//        Glide.with(holder.getContext())
//                .load(mPlayListBody.getPhotoRes())
//                .apply(RequestOptions.bitmapTransform(
//                        new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
//                .into(holder.mSmallPhoto);
        GlideHelper.load(mPlayListBody.getPhotoRes(),false,holder.mSmallPhoto);
        holder.mSmallPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("url",mPlayListBody.getPhotoRes());
            v.getContext().startActivity(intent);
        });
//        holder.mTitle.setText(mPlayListBody.getTitle());
//        holder.mPlaylist.setText(mPlayListBody.getPlaylist()[0] + "\n" + mPlayListBody.getPlaylist()[1] +"\n"+ mPlayListBody.getPlaylist()[2]);
        Utils.asyncSetText(holder.mTitle,mPlayListBody.getTitle());
        Utils.asyncSetText(holder.mPlaylist,mPlayListBody.getPlaylist()[0] + "\n" + mPlayListBody.getPlaylist()[1] +"\n"+ mPlayListBody.getPlaylist()[2]);
    }
    @Override
    public void viewRecycled(RecyclerHolder parentHolder, int position) {
        super.viewRecycled(parentHolder, position);
        Holder holder = (Holder) parentHolder;
    }
    @Override
    public int getViewResId() {
        return PLAYLIST_ITEM_RES_ID;
    }
    public static class Holder extends RecyclerHolder {

        private ImageView mPhotoView;
        private ImageView mPlay;
        private ImageView mSmallPhoto;
        private Button mBtnViewMore;
        private TextView mTitle;
        private TextView mPlaylist;
        public Holder(View itemView) {
            super(itemView);
            mPhotoView = itemView.findViewById(R.id.img_content);
            mSmallPhoto = itemView.findViewById(R.id.img_small_photo);
            mTitle = itemView.findViewById(R.id.tv_title);
            mPlaylist = itemView.findViewById(R.id.tv_playlist);
        }
    }
}
