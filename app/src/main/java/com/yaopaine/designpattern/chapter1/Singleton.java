package com.yaopaine.designpattern.chapter1;

/**
 * @Description 比较好的单例写法
 * @AuthorCreated yaopaine
 * @Version 1.0
 * @Time 1/31/18
 */

public class Singleton {

    private Singleton() {

    }

    public static Singleton getInstance() {
        return SingletonHolder.SINGLETON;
    }

    private Singleton readResolve() {
        return SingletonHolder.SINGLETON;
    }

    private static class SingletonHolder {
        private final static Singleton SINGLETON = new Singleton();
    }
}
