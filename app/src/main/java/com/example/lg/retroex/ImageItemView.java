package com.example.lg.retroex;

import android.content.Context;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by LG on 2016-02-02.
 */
public class ImageItemView extends FrameLayout {

    public ImageItemView(Context context) {
        super(context);
        init();
    }
    ImageView iconView;
    TextView titleView;
    public void init(){
        inflate(getContext(),R.layout.view_image_item,this);
        iconView = (ImageView)findViewById(R.id.imageView);
        titleView = (TextView)findViewById(R.id.textView);
    }

    public void setSearchResult(ImageItem item){
        titleView.setText(Html.fromHtml(Html.fromHtml(item.title).toString()));
        Glide.with(getContext())
                .load(item.image)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(iconView);



    }
}
