package com.yaopaine.designpattern.ocp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 1/30/18
 */

public class DiskCache implements ImageCache2 {

    private final static String cacheDir = "sdcard/cache/";

    @Override
    public Bitmap getBitmap(String imageUrl) {
        return BitmapFactory.decodeFile(cacheDir + imageUrl);
    }

    @Override
    public void putBitmap(String imageUrl, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(cacheDir + imageUrl);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
