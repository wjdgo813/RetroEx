package com.example.lg.retroex;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016-02-02.
 */
public class ImageAdapter extends BaseAdapter {
    List<ImageItem> items = new ArrayList<ImageItem>();

    String keyword;
    int totalCount;

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }
    public String getKeyword(){
        return this.keyword;
    }
    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
    public int getTotalCount(){
        return this.totalCount;
    }
    public int getStartIndex(){
        if(items.size() < totalCount){
            return items.size()+1;
        }
        return -1;
    }

    public void add(ImageItem item){
        items.add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItemView view;
        if(convertView ==null){
            view = new ImageItemView(parent.getContext());
        }else{
            view = (ImageItemView)convertView;
        }
        view.setSearchResult(items.get(position));

        return view;
    }
}
