package com.orangecaw.android.simage.data.source.local;

import com.orangecaw.android.simage.util.ArchiveUtil;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EBean
public class LocalSource {

    @Bean
    ArchiveUtil archiveUtil;

    public List<File> getStorageFiles() {
        File[] files = archiveUtil.getAppFolder().listFiles();
        return (files == null) ? new ArrayList<>() : new ArrayList<>(Arrays.asList(files));
    }

}
