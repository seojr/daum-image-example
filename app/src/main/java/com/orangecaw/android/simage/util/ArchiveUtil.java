package com.orangecaw.android.simage.util;

import android.content.Context;
import android.os.Environment;

import com.orangecaw.android.simage.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.File;

@EBean(scope = EBean.Scope.Singleton)
public class ArchiveUtil {

    @RootContext
    Context context;

    File appFolder;

    @AfterInject
    void setUp() {
        File picFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        appFolder = new File(picFolder, context.getString(R.string.app_folder_name));
        create();
    }

    void create() {
        if(!appFolder.exists()) {
            appFolder.mkdirs();
        }
    }

    public File getAppFolder() {
        return appFolder;
    }

    public String makeNewImagePath() {
        create();
        return new StringBuilder(appFolder.getAbsolutePath())
                .append("/")
                .append(System.currentTimeMillis())
                .append(".png")
                .toString();
    }

}
