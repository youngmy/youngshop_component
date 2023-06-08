package com.young.module.demo.bean;


import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.util.Objects;


public class StaggerdRecyclerViewBean {

    private String url;
    private String text;
    private final static String TAG = "StaggerdRecyclerViewBean";

    @BindingAdapter("url")
    public static void loadImg(ImageView imageView, String url) {
        Glide.with(imageView).load(url).into(imageView);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public StaggerdRecyclerViewBean(String url, String text) {
        this.url = url;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            //Log.i(TAG, ">>>>>>this==o return true");
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            //Log.i(TAG, ">>>>>>o==null||getClass()!=o.getClass() is false");
            return false;
        }
        StaggerdRecyclerViewBean rvBean = (StaggerdRecyclerViewBean) o;
        if (rvBean.url.length() != url.length() || rvBean.text.length() != text.length()) {
            //Log.i(TAG, ">>>>>>target length()!=existed url length");
            return false;
        }
        if(url.equals(rvBean.url)&&text.equals(rvBean.text)){
            //Log.i(TAG,">>>>>>url euqlas && text equals");
            return true;
        }else{
            //Log.i(TAG,">>>>>>not url euqlas && text equals");
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hashCode = Objects.hash(url, text);
        //Log.i(TAG, ">>>>>>hashCode->" + hashCode);
        return hashCode;
    }

}
