package com.fzy.single_ton;

/**
 * 懒汉模式（延迟加载）
 * 注：懒汉模式存在同步问题，会降低性能
 * Created by fuzhongyu on 2017/3/7.
 */
public class LazyBonesMode {

    //构造器私有，即不能被外部new
    private LazyBonesMode(){}

    private static LazyBonesMode lazyBonesMode;

    /**
     * 方法一：
     * 静态同步方法，使外界能访问到，保证多线程下安全
     *
     * 注：这种方式运行效率非常低
     * @return
     */
    public synchronized static LazyBonesMode getInstance(){
        if(lazyBonesMode==null){
            lazyBonesMode=new LazyBonesMode();
        }

        return lazyBonesMode;
    }

    /**
     * 方法二：
     * 利用DCL双检查锁机制，可以提高运行效率的同时，保证单例模式
     * @return
     */
    public static LazyBonesMode getInstance_2(){
        if(lazyBonesMode==null){
            synchronized (LazyBonesMode.class){
                if(lazyBonesMode==null){
                    lazyBonesMode=new LazyBonesMode();
                }
            }
        }
        return lazyBonesMode;
    }
}
