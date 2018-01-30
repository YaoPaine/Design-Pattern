package com.yaopaine.designpattern.ocp;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @Description 开闭原则
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 1/30/18
 */

public class MemoryCache implements ImageCache2 {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
        initCache();
    }

    private void initCache() {
        long l = Runtime.getRuntime().maxMemory() / 1024;
        int maxMemory = (int) (l / 4);
        mMemoryCache = new LruCache<String, Bitmap>(maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap getBitmap(String imageUrl) {
        return mMemoryCache.get(imageUrl);
    }

    @Override
    public void putBitmap(String imageUrl, Bitmap bitmap) {
        mMemoryCache.put(imageUrl, bitmap);
    }
}
