package com.fzy.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  (1) getHoldCount() 查询 "当前线程" 保持此锁定的个数，也就是调用lock()方法的次数
 *
 *  (2) getQueueLength() 返回正等待获取此锁定的线程估计数，如：有5个线程，在调用getQueueLength返回的是4，说明有4个线程正在等待lock释放锁
 *
 *  (3) getWaitQueueLength(Condition condition) 返回等待与此锁定相关的给定条件Condition的线程估计数，如：有5个线程，每一个都执行了"同一个condition"的await()，那么在调用getWaitQueueLength返回的是5
 *
 * Created by fuzhongyu on 2017/7/15.
 */
public class GetMethod {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    /**
     * 输出1和2
     */
    public static void test1(){
        GeMeService1 geMeService1=new GeMeService1();
        geMeService1.service1();
    }

    /**
     * 输出的都是1  因为getHoldCount()查询的是当前线程保持此锁定的个数
     */
    public static void test2(){
        GeMeService1 geMeService1=new GeMeService1();
        for (int i=0;i<5;i++){
            new Thread(i+""){
                @Override
                public void run(){
                    geMeService1.service2();
                }
            }.start();
        }
    }

    public static void test3(){
        GeMeService1 geMeService1=new GeMeService1();
        for (int i=0;i<5;i++){
            new Thread(i+""){
                @Override
                public void run(){
                    geMeService1.service3();
                    System.out.println("等待获得锁的线程数"+geMeService1.getLock().getQueueLength());
                }
            }.start();
        }
    }

    public static void test4(){
        GeMeService1 geMeService1=new GeMeService1();
        for (int i=0;i<5;i++){
            new Thread(i+""){
                @Override
                public void run(){
                    geMeService1.service4();
                }
            }.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        geMeService1.service5();
    }


}

class GeMeService1{

    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public ReentrantLock getLock() {
        return lock;
    }

    public void service1(){
        try {
            lock.lock();
            System.out.println("service1 getHoldCount="+lock.getHoldCount());
            service2();
        }finally {
            lock.unlock();
        }
    }

    public void service2(){
        try {
            lock.lock();
            System.out.println("service2 getHoldCount="+lock.getHoldCount());
        }finally {
            lock.unlock();
        }
    }

    public void service3(){
        try {
            lock.lock();
            System.out.println("线程"+Thread.currentThread().getName()+"进入方法");
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void service4(){
        try {
            lock.lock();
            condition.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void service5(){
        try {
            lock.lock();
            System.out.println("有"+lock.getWaitQueueLength(condition)+"个线程在等待");
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
