package com.yaopaine.designpattern.chapter2.builder;

import android.util.Log;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 2/3/18
 */

public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    private volatile static ImageLoader mImageLoader;
    private ImageLoaderConfig mImageLoaderConfig;

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (mImageLoader == null) {
            synchronized (com.yaopaine.designpattern.chapter2.ImageLoader.class) {
                if (mImageLoader == null) {
                    mImageLoader = new ImageLoader();
                }
            }
        }
        return mImageLoader;
    }

    public void init(ImageLoaderConfig imageLoaderConfig) {
        if (imageLoaderConfig == null) {
            throw new NullPointerException("init in ImageLoader imageLoaderConfig should not be null");
        }

        if (mImageLoaderConfig == null) {
            mImageLoaderConfig = imageLoaderConfig;
        } else {
            Log.w(TAG, "imageLoaderConfig has been initialized");
        }
    }

    private void checkImageLoaderConfig() {
        if (mImageLoaderConfig == null) {
            mImageLoaderConfig = ImageLoaderConfig.createDefaultConfig();
        }
    }
}
