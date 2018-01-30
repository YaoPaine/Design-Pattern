package com.yaopaine.designpattern.ocp;

import android.graphics.Bitmap;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 1/30/18
 */

public interface ImageCache2 {

    void putBitmap(String url, Bitmap bitmap);

    Bitmap getBitmap(String url);
}
