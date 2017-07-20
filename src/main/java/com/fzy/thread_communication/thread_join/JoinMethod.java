package com.fzy.thread_communication.thread_join;

/**
 * join(),一个线程等待另一个线程执行完毕再执行
 *
 * 注: （1）join具有是线程排队运行的作用，有些类似同步的运行效果，和synchronized的区别是join在内部是有wait()方法，而synchronized使用的是对象监视器原理作为同步
 *      (2) join内部使用了wait()方法，在线程中断的时候会产生中断异常
 *
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class JoinMethod {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){

       Thread thr1=new Thread("a"){
            @Override
            public void run(){
                System.out.println(Thread.currentThread().getName()+"正在执行");
            }
        };
       thr1.start();

        try {
            thr1.join();
            //线程等待1秒，如果该线程还存活，则不进行等待
//            thr1.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main 结束");
    }
}
