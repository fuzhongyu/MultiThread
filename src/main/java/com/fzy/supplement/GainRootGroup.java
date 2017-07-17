package com.fzy.supplement;

/**
 * 获取根线程组
 *
 * Created by fuzhongyu on 2017/7/16.
 */
public class GainRootGroup {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        System.out.println("线程："+Thread.currentThread().getName()+" 所在线程组名为："+Thread.currentThread().getThreadGroup().getName());

        System.out.println("main 线程所在的线程组的父线程组的名称是："+Thread.currentThread().getThreadGroup().getParent().getName());

        System.out.println("main 线程所在的线程组的父线程组的父线程组的名称是："+Thread.currentThread().getThreadGroup().getParent().getParent().getName());
    }

}
