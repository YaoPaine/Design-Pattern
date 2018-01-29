package com.yaopaine.designpattern.srp.chapter2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 2.0
 * @Time 1/26/18
 */

public class ImageLoader {

    private ImageCache mImageCache = new ImageCache();
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader() {

    }

    public void displayBitmap(final String url, final ImageView imageView) {
        imageView.setTag(imageView.getId(), url);
        Bitmap bitmap = mImageCache.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap downloadBitmap = downloadBitmap(url);
                if (downloadBitmap == null) return;
                mImageCache.putBitmap(url, downloadBitmap);
                if (url.equals(imageView.getTag(imageView.getId()))) {
                    imageView.setImageBitmap(downloadBitmap);
                }
            }
        });
    }

    public Bitmap downloadBitmap(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            urlConnection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }
        return null;
    }
}
