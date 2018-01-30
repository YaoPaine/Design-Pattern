package com.yaopaine.designpattern.ocp;

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
 * @Version 1.0
 * @Time 1/30/18
 */

public class ImageLoader2 {

    private ImageCache2 mImageCache = new ImageCache();
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void displayImage(ImageView imageView, String url) {
        Bitmap bitmap = mImageCache.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //图片没有缓存，提交到线程池中下载图片
        submitDownload(imageView, url);
    }

    private void submitDownload(final ImageView imageView, final String url) {
        imageView.setTag(imageView.getId(), url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = downloadBitmap(url);
                if (bmp == null) return;
                if (url.equals(imageView.getTag(imageView.getId()))) {
                    imageView.setImageBitmap(bmp);
                }
            }
        });
    }

    public Bitmap downloadBitmap(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            urlConnection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setImageCache(ImageCache2 imageCache) {
        this.mImageCache = imageCache;
    }
}
