package com.fzy.single_ton;


/**
 * 优化的懒汉模式（使用静态内置类实现单例）
 * 注：这种方式避免了同步锁带来的性能问题，也解决了对象过早初始化问题
 * Created by fuzhongyu on 2017/3/7.
 */
public class LazyBonesOptimizationMode{

    private LazyBonesOptimizationMode(){}

    //成员内部类
    private static class cinSideClass{
      private static LazyBonesOptimizationMode optimizationMode=new LazyBonesOptimizationMode();
    }

    public static LazyBonesOptimizationMode getInstance(){
        return cinSideClass.optimizationMode;
    }
}
