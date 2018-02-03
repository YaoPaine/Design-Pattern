package com.yaopaine.designpattern.chapter2.builder;

import com.yaopaine.designpattern.R;
import com.yaopaine.designpattern.chapter2.ImageCache;
import com.yaopaine.designpattern.chapter2.MemoryCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 2/3/18
 */

public class ImageLoaderConfig {

    private ImageCache imageCache;

    /**
     * 图片加载中显示的图片
     */
    private int loadingImage;
    /**
     * 加载失败时显示的图片
     */
    private int errorImage;
    /**
     * 图片加载线程池
     */
    ExecutorService executorService;

    private ImageLoaderConfig() {
    }

    /**
     * @param builder 事实上没有默认值，确必须的参数，需要验证参数的合法性
     */
    private ImageLoaderConfig(Builder builder) {
        this.imageCache = builder.imageCache;
        this.loadingImage = builder.loadingImage;
        this.errorImage = builder.errorImage;
        this.executorService = Executors.newFixedThreadPool(builder.threadCount);
    }

    public static ImageLoaderConfig createDefaultConfig() {
        return new Builder().build();
    }

    public static class Builder {
        private ImageCache imageCache = new MemoryCache();

        /**
         * 图片加载中显示的图片
         */
        private int loadingImage;
        /**
         * 加载失败时显示的图片
         */
        private int errorImage;
        /**
         * 图片加载线程池
         */
        private int threadCount = Runtime.getRuntime().availableProcessors();

        /**
         * 设置缓存
         */
        public Builder setCache(ImageCache imageCache) {
            checkObjectNull(imageCache);
            this.imageCache = imageCache;
            return this;
        }

        public Builder setLoadingImage(int loadingImage) {
            this.loadingImage = loadingImage;
            return this;
        }

        public Builder setErrorImage(int errorImage) {
            this.errorImage = errorImage;
            return this;
        }

        public Builder setThreadCount(int threadCount) {
            this.threadCount = threadCount;
            return this;
        }

        public ImageLoaderConfig build() {
            return new ImageLoaderConfig(this);
        }

        private void checkObjectNull(Object object) {
            if (object == null) {
                throw new NullPointerException("null parameter");
            }
        }
    }
}
