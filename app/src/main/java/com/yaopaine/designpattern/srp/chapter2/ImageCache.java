package com.yaopaine.designpattern.srp.chapter2;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 1/26/18
 */

public class ImageCache {

    private LruCache<String, Bitmap> mImageCache;

    public ImageCache() {
        initCache();
    }

    private void initCache() {
        long l = Runtime.getRuntime().maxMemory() / 1024;
        int maxMemory = (int) (l / 4);
        mImageCache = new LruCache<String, Bitmap>(maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public void putBitmap(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }

    public Bitmap getBitmap(String url) {
        return mImageCache.get(url);
    }
}
