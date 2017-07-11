package com.fzy.single_ton;

/**
 * 饿汉模式（立即加载）
 * 注：(1)饿汉模式在类初始化的时候就创建了对象，一般我们在需要使用的时候才创建对象
 *    (2) 不能有其他实例变量，因为getInstance()方法没有同步，所以可能会出现线程安全问题
 * Created by fuzhongyu on 2017/3/7.
 */
public class StarveMode {

    //通过静态变量同步机制来实现单例
    private static StarveMode starveMode=new StarveMode();

    private StarveMode(){}

    public static StarveMode getInstance(){
        return starveMode;
    }
}
