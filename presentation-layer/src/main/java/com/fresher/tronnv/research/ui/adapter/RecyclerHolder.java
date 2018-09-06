package com.fresher.tronnv.research.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Modified RecyclerView.ViewHolder
 */
public class RecyclerHolder extends RecyclerView.ViewHolder  {

    public View mItemView;

    public RecyclerHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    /**
     * Convenient method to get a Context
     * @return a Context, or null if no Views have been attached
     */
    public Context getContext() {
        if (mItemView != null) {
            return mItemView.getContext();
        }
        return null;
    }
}