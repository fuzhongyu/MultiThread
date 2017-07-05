package com.fzy.normal_method;

/**
 *  线程优先级
 *
 *  线程优先级分为1~10十个等级，如果小于1或者大于10则会抛出异常，jdk用3个常量（MIN_PRIORITY=1，NORM_PRIORITY=5，MAX_PRIORITY=10）来预置优先级的值
 *
 *  cup会尽量把资源给线程优先级高的线程，但优先级高的线程也不一定每一次都先执行完
 *
 * Created by fuzhongyu on 2017/7/4.
 */
public class Priority {


    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        System.out.println("main thread begin priority="+Thread.currentThread().getPriority());
        PriThr1 thr1=new PriThr1();
        thr1.setPriority(Thread.MAX_PRIORITY);
        thr1.start();
        System.out.println("main thread end");
    }

}


/**
 * 线程的优先级具有继承性，如thr1线程启动thr2,则thr2线程优先级与thr1是一样的
 */
class PriThr1 extends Thread{

    @Override
    public void run(){
        System.out.println("thr1 run priority="+this.getPriority());
        PriThr2 thr2=new PriThr2();
        thr2.start();
    }
}

class PriThr2 extends  Thread{

    @Override
    public void run(){
        System.out.println("thr2 run priority="+this.getPriority());
    }
}
