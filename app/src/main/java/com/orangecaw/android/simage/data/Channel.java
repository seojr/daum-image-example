package com.orangecaw.android.simage.data;

import java.util.List;

import lombok.Getter;

public class Channel {

    private String title;
    private String link;
    private String description;
    private String lastBuildDate;
    private int totalCount;
    private int pageCount;
    private int result;
    @Getter private List<Image> item;

}
