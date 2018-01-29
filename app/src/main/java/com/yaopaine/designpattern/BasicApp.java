package com.yaopaine.designpattern;

import android.app.Application;

import com.yaopaine.designpattern.srp.chapter1.ImageLoader;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 1/27/18
 */

public class BasicApp extends Application {

    private static ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        mImageLoader = new ImageLoader();
    }

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
