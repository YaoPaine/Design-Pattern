package com.yaopaine.designpattern.chapter2;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 2/3/18
 */

public class ImageLoader {

    private volatile static ImageLoader mImageLoader;
    private ImageCache mImageCache = new MemoryCache();

    /**
     * 图片加载中显示的图片
     */
    private int mLoadingImage;
    /**
     * 加载失败时显示的图片
     */
    private int mErrorImage;
    /**
     * 图片加载线程池
     */
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (mImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (mImageLoader == null) {
                    mImageLoader = new ImageLoader();
                }
            }
        }
        return mImageLoader;
    }

    public void displayBitmap(String imageUrl, ImageView imageView) {
        Bitmap bitmap = mImageCache.getBitmap(imageUrl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        submitLoadRequest(imageUrl, imageView);
    }

    private void submitLoadRequest(final String imageUrl, final ImageView imageView) {
        imageView.setTag(imageView.getId(), imageUrl);
        imageView.setImageResource(mLoadingImage);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadBitmap(imageUrl);
                String urlTag = (String) imageView.getTag(imageView.getId());
                if (!imageUrl.equals(urlTag)) return;
                if (bitmap == null) {
                    imageView.setImageResource(mErrorImage);
                } else {
                    imageView.setImageBitmap(bitmap);
                    mImageCache.putBitmap(imageUrl, bitmap);
                }
            }
        });
    }

    /**
     * @param url 根据url下载图片
     * @return
     */
    public Bitmap downloadBitmap(String url) {
        return null;
    }

    public void setImageCache(ImageCache imageCache) {
        this.mImageCache = imageCache;
    }

    public void setmLoadingImage(int loadingImage) {
        this.mLoadingImage = loadingImage;
    }

    public void setmErrorImage(int errorImage) {
        this.mErrorImage = errorImage;
    }

    /**
     * 重新设置threadCount
     *
     * @param threadCount
     */
    public void setThreadCount(int threadCount) {
        mExecutorService.shutdown();
        mExecutorService = null;
        mExecutorService = Executors.newFixedThreadPool(threadCount);
    }
}
