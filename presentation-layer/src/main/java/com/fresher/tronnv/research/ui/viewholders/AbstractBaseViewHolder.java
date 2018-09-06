package com.fresher.tronnv.research.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class AbstractBaseViewHolder <T> extends RecyclerView.ViewHolder {
    AbstractBaseViewHolder(View view) {
        super(view);
    }
    public abstract void bind(T element);
}