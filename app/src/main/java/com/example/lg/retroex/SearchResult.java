package com.example.lg.retroex;

import android.text.Html;

import java.io.Serializable;

/**
 * Created by LG on 2016-01-28.
 */
public class SearchResult implements Serializable {
    ImageInfo channel;
    public SearchResult(ImageInfo channel){
       this.channel=channel;
    }
}
