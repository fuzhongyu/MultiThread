package com.fzy.supplement;

/**
 * 线程组自动归属特性, 在实例化一个线程组的时候，如果不指定归属的线程组，则自动归到 " 当前线程对象所属的线程组 " 中
 *
 * Created by fuzhongyu on 2017/7/16.
 */
public class AutoBelongGroup {

    public static void main(String[] args) {
       test1();
    }

    public static void test1(){
        System.out.println("A处线程："+Thread.currentThread().getName()+
                " 所属的线程组名为："+Thread.currentThread().getThreadGroup().getName()+
                " 中有线程组数："+Thread.currentThread().getThreadGroup().activeGroupCount());

        ThreadGroup newGroup=new ThreadGroup("new_group");


        System.out.println("B处线程："+Thread.currentThread().getName()+
                " 所属的线程组名为："+Thread.currentThread().getThreadGroup().getName()+
                " 中有线程组数："+Thread.currentThread().getThreadGroup().activeGroupCount());

        ThreadGroup[] groups=new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
        Thread.currentThread().getThreadGroup().enumerate(groups);

        for (ThreadGroup group:groups){
            System.out.println(group.getName());
        }
    }

}
