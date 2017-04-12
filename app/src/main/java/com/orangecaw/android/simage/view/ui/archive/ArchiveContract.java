package com.orangecaw.android.simage.view.ui.archive;

import com.orangecaw.android.simage.view.base.BasePresenter;
import com.orangecaw.android.simage.view.base.BaseView;

import java.io.File;
import java.util.List;

public interface ArchiveContract {

    interface View extends BaseView {

        void setFiles(List<File> files);

    }

    interface Presenter extends BasePresenter<View> {

        void loadFiles();

    }

}
