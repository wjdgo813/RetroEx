package com.example.lg.retroex;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LG on 2016-02-02.
 */
public class ImageInfo implements Serializable {
    int totalCount;
    List<ImageItem> item;
    public ImageInfo(int totalCount,List<ImageItem> item){
        this.totalCount=totalCount;
        this.item = item;
    }
}
