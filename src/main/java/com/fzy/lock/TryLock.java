package com.fzy.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *  (1) lockInterruptibly()  如果当前线程未被中断，则获取锁定，如果已经被中断则出现异常
 *
 *  (2) tryLock() 仅在调用时锁定未被另一个线程保持的情况下，才获取该锁定
 *
 *  (3) tryLock(long timeout,TimeUnit unit)如果锁在给定等待时间内没有被另一个线程保持，且当前线程未中断，则获取该锁定
 *
 * Created by fuzhongyu on 2017/7/15.
 */
public class TryLock {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    /**
     * 没有出现异常，thr1和thr2正常执行，说明线程被中断lock()不出现异常，正常执行
     */
    public static void test1(){
        TrLocService1 trLocService1=new TrLocService1();

        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                trLocService1.service1();
            }
        };

        Thread thr2=new Thread("b"){
            @Override
            public void run(){
                trLocService1.service1();
            }
        };

        thr1.start();
        thr2.start();
        //中断标记
        thr2.interrupt();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" main end");
    }

    /**
     * 出现异常，说明lockInterruptibly()在线程中断标记的时候，会抛出异常
     */
    public static void test2(){
        TrLocService1 trLocService1=new TrLocService1();

        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                trLocService1.service2();
            }
        };

        Thread thr2=new Thread("b"){
            @Override
            public void run(){
                trLocService1.service2();
            }
        };

        thr1.start();
        thr2.start();
        //中断标记
        thr2.interrupt();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" main end");
    }


    public static void test3(){
        TrLoService2 trLoService2=new TrLoService2();
        new Thread("a"){
            @Override
            public void run(){
                trLoService2.service1();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                trLoService2.service1();
            }
        }.start();
    }
}

class TrLocService1{
    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void service1(){
        try {
            lock.lock();
            System.out.println("lock begin " + System.currentTimeMillis());
            for (int i = 0; i < Integer.MAX_VALUE / 10; i++) {
                String str = new String();
                Math.random();
            }
            System.out.println("lock end " + System.currentTimeMillis());
        }finally {
            if(lock.isHeldByCurrentThread()){
                System.out.println("当前线程保持锁");
                lock.unlock();
            }
        }
    }

    public void service2(){
        try {
            lock.lockInterruptibly();
            System.out.println("lock begin "+System.currentTimeMillis());
            for (int i = 0; i < Integer.MAX_VALUE / 10; i++) {
                String str = new String();
                Math.random();
            }
            System.out.println("lock end "+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            if(lock.isHeldByCurrentThread()){
                System.out.println("当前线程保持锁");
                lock.unlock();
            }
        }
    }
}

class TrLoService2{
    private ReentrantLock lock=new ReentrantLock();
    public void service1(){
        if (lock.tryLock()){
            System.out.println(Thread.currentThread().getName()+"获得锁");
        }else {
            System.out.println(Thread.currentThread().getName()+"未获得锁");
        }
    }
}

class TrLoService3{
    private ReentrantLock lock=new ReentrantLock();

}