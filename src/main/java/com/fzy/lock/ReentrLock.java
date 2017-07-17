package com.fzy.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock
 *
 * Created by fuzhongyu on 2017/7/15.
 */
public class ReentrLock {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    /**
     * 线程打印完毕后才将锁释放，其他线程才可以打印
     */
    public static void test1(){
        ReLoService1 reLoService=new ReLoService1();
        for (int i=0;i<5;i++){
            new Thread(i+""){
                @Override
                public void run(){
                    reLoService.service1();
                }
            }.start();
        }
    }

    /**
     * service1和service2输出有1秒时间间隔（同一个Lock,说明同一个Lock使用的是同一个对象监视器
     * 注：同一个lock可通过condition创建不同的对象监视器
     */
    public static void test2(){
        ReLoService2 reLoService2=new ReLoService2();
        new Thread("a"){
            @Override
            public void run(){
                reLoService2.service1();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                reLoService2.service2();
            }
        }.start();
    }


    /**
     * service3和service2输出没有时间间隔(不同Lock),说明不同lock使用的是不同的对象监视器
     */
    public static void test3(){
        ReLoService2 reLoService2=new ReLoService2();
        new Thread("a"){
            @Override
            public void run(){
                reLoService2.service2();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                reLoService2.service3();
            }
        }.start();
    }
}

class ReLoService1{

    private Lock lock=new ReentrantLock();

    public void service1(){
        lock.lock();
        for (int i=0;i<5;i++){
            System.out.println("线程:"+Thread.currentThread().getName()+"打印"+i);
        }
        lock.unlock();
    }
}

class ReLoService2{

    private Lock lock1=new ReentrantLock();
    private Lock lock2=new ReentrantLock();

    public void service1(){
        lock1.lock();
        try {
            System.out.println("----service1----");
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock1.unlock();
        }
    }

    public void service2(){
        lock1.lock();
        try {
            System.out.println("----service2----");
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock1.unlock();
        }
    }

    public void service3(){
        lock2.lock();
        try {
            System.out.println("----service3----");
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock2.unlock();
        }
    }
}


