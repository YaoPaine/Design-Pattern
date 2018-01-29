package com.yaopaine.designpattern.srp.chapter1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;


/**
 * @Description ImageLoader 单一原则
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 1/26/18
 */

//图片加载
public class ImageLoader {
    //图片缓存
    private LruCache<String, Bitmap> mImageCache;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private final static String TAG = "ImageLoader";

    public ImageLoader() {
        initCache();
    }

    private void initCache() {
        //计算可使用的最大内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        Log.e(TAG, "initCache: maxMemory:" + maxMemory);
        Log.e(TAG, "initCache: availableProcessors:" + Runtime.getRuntime().availableProcessors());
        int mMemory = (int) (maxMemory / 1024);
        int cacheSize = mMemory / 4;//取四分之一的可用内存作为缓存
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                Log.e(TAG, "sizeOf: getRowBytes:" + value.getRowBytes());
                Log.e(TAG, "sizeOf: getByteCount:" + value.getByteCount());
                Log.e(TAG, "sizeOf: getHeight:" + value.getHeight());
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public void displayImage(final String url, final ImageView imageView) {
        imageView.setTag(imageView.getId(), url);
        Bitmap bitmap1 = mImageCache.get(url);
        if (bitmap1 != null) {
            imageView.setImageBitmap(bitmap1);
            return;
        }
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadBitmap(url);
                if (bitmap == null) return;
                if (url.equals(imageView.getTag(imageView.getId()))) {
                    imageView.setImageBitmap(bitmap);
                    mImageCache.put(url, bitmap);
                }
            }
        });
    }

    /**
     * @param imageUrl 网络图片地址
     * @return bitmap
     */
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

}
