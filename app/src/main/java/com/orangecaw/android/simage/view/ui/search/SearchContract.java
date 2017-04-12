package com.orangecaw.android.simage.view.ui.search;

import android.support.annotation.StringRes;
import android.support.v7.widget.SearchView;

import com.orangecaw.android.simage.data.Image;
import com.orangecaw.android.simage.view.base.BasePresenter;
import com.orangecaw.android.simage.view.base.BaseView;

import java.util.List;

public interface SearchContract {

    interface View extends BaseView {

        void setImages(List<Image> images);

        void showToast(@StringRes int stringRes);

    }

    interface Presenter extends BasePresenter<View> {

        void watchSearchView(SearchView searchView);

        void waitDownload(Image image);

        void download();

        void download(Image image);

    }

}
