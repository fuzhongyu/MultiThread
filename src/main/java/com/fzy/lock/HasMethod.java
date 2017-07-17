package com.fzy.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * (1) hasQueueThread(Thread thread) 查询指定的线程是否正在等待获取此锁定
 *
 * (2) hasQueueThreads() 查询是否有线程正在等待获取此锁定
 *
 * (3) hasWaiters(Condition condition) 查询是否有线程正在等待与此锁有关的condition条件
 *
 * Created by fuzhongyu on 2017/7/15.
 */
public class HasMethod {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * hasQueueThread和hasQueueThreads方法
     */
    public static void test1(){
        HaMeService1 haMeService1=new HaMeService1();
        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                haMeService1.serice1();
            }
        };
        thr1.start();


        Thread thr2=new Thread("b"){
            @Override
            public void run(){
                haMeService1.serice1();
            }
        };
        thr2.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(haMeService1.getLock().hasQueuedThread(thr1));
        System.out.println(haMeService1.getLock().hasQueuedThread(thr2));
        System.out.println(haMeService1.getLock().hasQueuedThreads());

    }

    /**
     * hasWaiters方法
     */
    public static  void test2(){
        HaMeService1 haMeService1=new HaMeService1();
        for (int i=0;i<5;i++){
            new Thread(i+""){
                @Override
                public void run(){
                    haMeService1.service4();
                }
            }.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        haMeService1.service5();
    }
}

class HaMeService1{

    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public ReentrantLock getLock() {
        return lock;
    }

    public void serice1(){
        try {
            lock.lock();
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
            System.out.println("有没有正在等待condition的线程？"+lock.hasWaiters(condition)+"  有"+lock.getWaitQueueLength(condition)+"个线程在等待");
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}


