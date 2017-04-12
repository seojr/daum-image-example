package com.orangecaw.android.simage.view.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewHolder<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    public RecyclerViewHolder(V itemView) {
        super(itemView);
        this.view = itemView;
    }

    public V getView() {
        return view;
    }

}
