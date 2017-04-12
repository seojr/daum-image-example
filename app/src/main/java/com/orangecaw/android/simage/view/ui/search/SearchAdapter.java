package com.orangecaw.android.simage.view.ui.search;

import android.content.Context;
import android.view.ViewGroup;

import com.orangecaw.android.simage.data.Image;
import com.orangecaw.android.simage.view.widget.ImageItemView;
import com.orangecaw.android.simage.view.base.RecyclerViewAdapterBase;
import com.orangecaw.android.simage.view.base.RecyclerViewHolder;
import com.orangecaw.android.simage.view.listener.RecyclerViewItemClickListener;
import com.orangecaw.android.simage.view.widget.ImageItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class SearchAdapter extends RecyclerViewAdapterBase<Image, ImageItemView<String>> {

    @RootContext
    Context context;

    private RecyclerViewItemClickListener itemClickListener;

    public void setImages(List<Image> images) {
        this.list = images;
    }

    public void setOnItemClickListener(RecyclerViewItemClickListener<Image> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected ImageItemView<String> onCreateItemView(ViewGroup parent, int viewType) {
        return ImageItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder<ImageItemView<String>> holder, int position) {
        ImageItemView<String> view = holder.getView();
        view.bind(list.get(position).getThumbnail());
        view.setOnClickListener(v -> {
            if(itemClickListener != null) {
                itemClickListener.onItemClick(list.get(position));
            }
        });
    }

}
