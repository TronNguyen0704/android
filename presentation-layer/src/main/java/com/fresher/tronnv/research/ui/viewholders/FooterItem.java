package com.fresher.tronnv.research.ui.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fresher.tronnv.android_models.Footer;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.ui.adapter.RecyclerHolder;
import com.fresher.tronnv.research.ui.adapter.RecyclerItem;

public class FooterItem extends RecyclerItem {
    public static final int FOOTER_ITEM_RES_ID = R.layout.footer_layout;
    private Footer mFooter;
    public FooterItem(Footer footer){
        super();
        this.mFooter = footer;
    }
    @Override
    public void updateView(RecyclerHolder parentHolder, int position) {
        Holder holder = (Holder) parentHolder;
//        holder.mTextLikes.setText("❤ " + mFooter.getLike());
        Utils.asyncSetText(holder.mTextLikes,"❤ " +mFooter.getLike());

    }
    @Override
    public void viewRecycled(RecyclerHolder parentHolder, int position) {
        super.viewRecycled(parentHolder, position);

        Holder holder = (Holder) parentHolder;
    }
    @Override
    public int getViewResId() {
        return FOOTER_ITEM_RES_ID;
    }
    public static class Holder extends RecyclerHolder {
        private Button mBtnComment;
        private Button mBtnShare;
        private TextView mTextLikes;

        public Holder(View itemView) {
            super(itemView);
            mBtnComment = itemView.findViewById(R.id.btn_comment);
            mBtnShare = itemView.findViewById(R.id.btn_share);
            mTextLikes = itemView.findViewById(R.id.tv_likes);
        }
    }
}
