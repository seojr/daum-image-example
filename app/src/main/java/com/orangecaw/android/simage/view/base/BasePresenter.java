package com.orangecaw.android.simage.view.base;

public interface BasePresenter<V extends BaseView> {

    void setView(V view);

    void unSubscribe();

}
