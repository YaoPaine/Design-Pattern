package com.yaopaine.designpattern.chapter2;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 2/3/18
 */

public class MemoryCache implements ImageCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        mMemoryCache = new LruCache<String, Bitmap>(maxMemory / 4) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mMemoryCache.get(url);
    }
}
