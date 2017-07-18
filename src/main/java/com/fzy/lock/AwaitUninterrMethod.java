package com.fzy.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * awaitUninterruptibly() 方法，在await的时候不产生线程中断异常，且中断了不影响线程运行
 *
 * Created by fuzhongyu on 2017/7/18.
 */
public class AwaitUninterrMethod {

    public static void main(String[] args) {
//      test1();
        test2();
    }

    /**
     * 线程在wait的时候，中断会出现中断异常
     */
    public static void test1(){
        AwUnMeService1 awUnMeService1=new AwUnMeService1();

        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                awUnMeService1.service1();
            }
        };
        thr1.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thr1.interrupt();

    }

    /**
     * 没有出现中断异常，且线程保持正常运行
     */
    public static void test2(){
        AwUnMeService1 awUnMeService1=new AwUnMeService1();

        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                awUnMeService1.service2();
            }
        };
        thr1.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thr1.interrupt();
    }
}

class AwUnMeService1{

    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void service1(){
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" wait begin");
            condition.await();
            System.out.println(Thread.currentThread().getName()+" wait end");
        }catch (InterruptedException e){
            e.printStackTrace();
            System.out.println("出现异常");
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }


    public void service2(){
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" wait begin");
            condition.awaitUninterruptibly();
            System.out.println(Thread.currentThread().getName()+" wait end");
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }


}
