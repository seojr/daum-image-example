package com.orangecaw.android.simage.view.ui.archive;

import android.content.Context;
import android.view.ViewGroup;

import com.orangecaw.android.simage.view.base.RecyclerViewAdapterBase;
import com.orangecaw.android.simage.view.base.RecyclerViewHolder;
import com.orangecaw.android.simage.view.widget.ImageItemView;
import com.orangecaw.android.simage.view.widget.ImageItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.File;
import java.util.List;

@EBean
public class ArchiveAdapter extends RecyclerViewAdapterBase<File, ImageItemView<File>> {

    @RootContext
    Context context;

    public void setFiles(List<File> files) {
        this.list = files;
        notifyDataSetChanged();
    }

    public void addFile(File file, int position) {
        this.list.add(position, file);
        notifyDataSetChanged();
    }

    @Override
    protected ImageItemView<File> onCreateItemView(ViewGroup parent, int viewType) {
        return ImageItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder<ImageItemView<File>> holder, int position) {
        ImageItemView<File> view = holder.getView();
        view.bind(list.get(position));
    }

}
