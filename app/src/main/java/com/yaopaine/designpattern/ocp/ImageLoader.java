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

public class ImageLoader {

    private boolean isUseDoubleCache = false;
    private boolean isUseDiskCache = false;
    private ImageCache mImageCache = new ImageCache();
    private DiskCache mDiskCache = new DiskCache();
    private MemoryCache mMemoryCache = new MemoryCache();

    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void displayImage(final ImageView imageView, final String url) {
        imageView.setTag(imageView.getId(), url);
        Bitmap bitmap;
        if (isUseDoubleCache) {
            bitmap = mImageCache.getBitmap(url);
        } else if (isUseDiskCache) {
            bitmap = mDiskCache.getBitmap(url);
        } else {
            bitmap = mMemoryCache.getBitmap(url);
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
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

    public void setUseDiskCache(boolean useDiskCache) {
        isUseDiskCache = useDiskCache;
    }

    public void setUseDoubleCache(boolean useDoubleCache) {
        isUseDoubleCache = useDoubleCache;
    }
}
