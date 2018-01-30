package com.yaopaine.designpattern.ocp;

import android.graphics.Bitmap;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 1/30/18
 */

public class ImageCache implements ImageCache2 {

    private volatile MemoryCache mMemoryCache;
    private volatile DiskCache mDiskCache;

    public ImageCache() {
        if (mMemoryCache == null) {
            mMemoryCache = new MemoryCache();
        }

        if (mDiskCache == null) {
            mDiskCache = new DiskCache();
        }
    }

    @Override
    public Bitmap getBitmap(String imageUrl) {
        Bitmap bitmap = mMemoryCache.getBitmap(imageUrl);
        if (bitmap == null) {
            bitmap = mDiskCache.getBitmap(imageUrl);
        }
        return bitmap;
    }

    @Override
    public void putBitmap(String imageUrl, Bitmap bitmap) {
        mMemoryCache.putBitmap(imageUrl, bitmap);
        mDiskCache.putBitmap(imageUrl, bitmap);
    }
}
