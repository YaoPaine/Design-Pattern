package com.yaopaine.designpattern.chapter2;

import android.graphics.Bitmap;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 2/3/18
 */

public interface ImageCache {

    void putBitmap(String url, Bitmap bitmap);

    Bitmap getBitmap(String url);
}
