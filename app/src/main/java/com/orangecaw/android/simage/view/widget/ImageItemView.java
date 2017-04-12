package com.orangecaw.android.simage.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.orangecaw.android.simage.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.image_item)
public class ImageItemView<T> extends LinearLayout {

    @ViewById(R.id.item_image)
    ImageView itemImage;

    public ImageItemView(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void bind(T image) {
        Glide.with(getContext())
                .load(image)
                .asBitmap()
                .centerCrop()
                .animate(android.R.anim.fade_in)
                .into(itemImage);
    }

}
