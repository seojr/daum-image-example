package com.orangecaw.android.simage.util;

import java.io.File;
import java.util.Comparator;

public class FileDateDescComparator implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        return (o2.lastModified() - o1.lastModified()) > 0 ? 1 : -1;
    }

}
