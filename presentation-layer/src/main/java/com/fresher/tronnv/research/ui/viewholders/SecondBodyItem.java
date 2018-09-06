package com.fresher.tronnv.research.ui.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.android_models.SecondBody;
import com.fresher.tronnv.research.GlideHelper;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.ui.adapter.RecyclerHolder;
import com.fresher.tronnv.research.ui.adapter.RecyclerItem;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class SecondBodyItem extends RecyclerItem {
    public static final int SECONDBODY_ITEM_RES_ID = R.layout.secondbody_layout;
    private SecondBody mSecondBody;
    public SecondBodyItem(SecondBody mSecondBody){
        super();
        this.mSecondBody = mSecondBody;
    }
    @Override
    public void updateView(RecyclerHolder parentHolder, int position) {
        Holder holder = (Holder) parentHolder;
//        Glide.with(holder.getContext())
//                .load(mSecondBody.getPhotoRes())
//                .apply(RequestOptions.bitmapTransform(
//                        new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
//                .into(holder.mPhotoView);
        GlideHelper.load(mSecondBody.getPhotoRes(),false,holder.mPhotoView);
    }
    @Override
    public void viewRecycled(RecyclerHolder parentHolder, int position) {
        super.viewRecycled(parentHolder, position);

        Holder holder = (Holder) parentHolder;
    }
    @Override
    public int getViewResId() {
        return SECONDBODY_ITEM_RES_ID;
    }
    public static class Holder extends RecyclerHolder {

        private ImageView mPhotoView;
        private ImageView mPlay;
        public Holder(View itemView) {
            super(itemView);
            mPhotoView = itemView.findViewById(R.id.img_content);
        }
    }
}
