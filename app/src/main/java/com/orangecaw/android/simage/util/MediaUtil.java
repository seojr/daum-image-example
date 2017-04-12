package com.orangecaw.android.simage.util;

import android.content.Context;
import android.media.MediaScannerConnection;

public class MediaUtil {

    public static void scanImage(Context context, String filePath) {
        MediaScannerConnection.scanFile(context, new String[]{filePath}, new String[]{"image/jpg"}, null);
    }

}
