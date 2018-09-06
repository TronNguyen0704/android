package com.fresher.tronnv.research.ui.viewholders;

import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fresher.tronnv.android_models.FirstBody;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.ui.ExpandableTextView;
import com.fresher.tronnv.research.ui.adapter.RecyclerHolder;
import com.fresher.tronnv.research.ui.adapter.RecyclerItem;

public class FirstBodyItem extends RecyclerItem {
    public static final int FIRSTBODY_ITEM_RES_ID = R.layout.firstbody_layout;
    private FirstBody mFirstBody;
    private SpannableStringBuilder mSpannableString;
    static String mContent;
    public FirstBodyItem(FirstBody firstBody){
        super();
        mFirstBody = firstBody;
        String value = mFirstBody.getContent();
        mSpannableString = new SpannableStringBuilder(value);
        mContent = value;
    }
    @Override
    public void updateView(RecyclerHolder parentHolder, int position) {
        Holder holder = (Holder) parentHolder;
//        if(position % 2 == 0){
//            holder.mTextContent.setTextSize(14);
//            holder.mTextContent.setTextColor(Color.BLUE);
//        }else{
//            holder.mTextContent.setTextSize(16);
//            holder.mTextContent.setTextColor(Color.YELLOW);
//        }
        holder.mTextContent.setText(mFirstBody.getContent());
//        Utils.asyncSetText3(holder.mTextContent,mSpannableString);
    }
    @Override
    public void viewRecycled(RecyclerHolder parentHolder, int position) {
        super.viewRecycled(parentHolder, position);
        Holder holder = (Holder) parentHolder;
    }
    @Override
    public int getViewResId() {
        return FIRSTBODY_ITEM_RES_ID;
    }
    public static class Holder extends RecyclerHolder {
        private TextView mTextContent;
        private Button mViewMore;

        public Holder(View itemView) {
            super(itemView);
            mTextContent = itemView.findViewById(R.id.tv_content);
            mTextContent.setMovementMethod(LinkMovementMethod.getInstance());
//            ExpandableTextView.doResizeTextView(mContent,mTextContent, 4, "View more", true);
        }
    }
}
