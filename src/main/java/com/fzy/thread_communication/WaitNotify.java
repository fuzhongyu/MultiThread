package com.fzy.thread_communication;


/**
 * 等待通知机制
 *
 * 注：
 *   (1)wait()和notify()都是object的类方法，在调用wait()和notify()方法之前线程必须获得该对象的对象锁，否则会抛出IllegalMonitorStateException异常
 *   (2)在执行wait()方法后，当前线程释放锁
 *  （3）notify()方法，通知等待该对象的对象锁的线程，随机发出通知，使其中一个对象获得对象锁
 *  （4）在执行notify方法后，当前线程不会马上释放该对象锁，呈wait状态的线程也不会马上获取到该对象锁，要等到执行notify()方法的线程将程序执行完，
 *      也就是出了synchronized代码块后，当前线程才能释放锁，而wait的线程才能获取该对象锁。
 *  （5）当第一个获得锁对象的wait线程运行完毕后，他会释放掉该对象的锁，如果此时没有对象再使用notify，则即便该对象已经空闲，其他wait状态的线程由于没有通知还会阻塞在wait状态
 *
 * Created by fuzhongyu on 2017/7/7.
 */
public class WaitNotify {

    public static void main(String[] args) {
        test1();
//        test2();
//        test3();
    }

    /**
     * 会抛出IllegalMonitorStateException异常
     */
    public static void test1(){
        try {
            Object obj=new Object();
            obj.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 由于没有通知wait的线程，所以会一直停留在wait状态
     */
    public static void test2(){
        try {
            Object obj=new Object();
            synchronized (obj){
                System.out.println("--syn start---");
                obj.wait();
                System.out.println("syn end ---");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("==== main end ====");
    }


    /**
     * 线程b通知到线程a，线程a往下执行
     */
    public static void test3(){

        Object obj=new Object();

        new Thread("a"){
            @Override
            public void run(){
                try {
                    synchronized (obj){
                        System.out.println("thr name="+Thread.currentThread().getName()+" start--- time="+System.currentTimeMillis());
                        obj.wait();
                        System.out.println("thr name="+Thread.currentThread().getName()+" end --time="+System.currentTimeMillis());
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("b"){
            @Override
            public void run(){
                synchronized (obj){
                    System.out.println("--"+Thread.currentThread().getName()+" start---");
                    obj.notify();
                    System.out.println("--"+Thread.currentThread().getName()+" end ---");
                }
            }
        }.start();

    }


}


