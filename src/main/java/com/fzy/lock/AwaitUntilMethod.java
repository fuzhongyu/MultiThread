package com.fzy.lock;

import java.util.Calendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  condition.awaitUntil() 可设定等待到什么时间点， condition.await()不可设定时间
 * Created by fuzhongyu on 2017/7/18.
 */
public class AwaitUntilMethod {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1(){
        AwUntilMeService1 awUntilMeService1=new AwUntilMeService1();

        new Thread("a"){
            @Override
            public void run(){
                awUntilMeService1.service1();
            }
        }.start();
    }


    public static void test2(){
        AwUntilMeService1 awUntilMeService1=new AwUntilMeService1();

        new Thread("a"){
            @Override
            public void run(){
                awUntilMeService1.service1();
            }
        }.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("b"){
            @Override
            public void run(){
                awUntilMeService1.service2();
            }
        }.start();


    }
}

class AwUntilMeService1{

    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void service1(){
        try {
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.SECOND,10);
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" wait begin time:"+System.currentTimeMillis());
            condition.awaitUntil(calendar.getTime());
            System.out.println(Thread.currentThread().getName()+" wait end time:"+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void service2(){
        try {
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.SECOND,10);
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" notify begin time:"+System.currentTimeMillis());
            condition.signalAll();
            System.out.println(Thread.currentThread().getName()+" notify end time :"+System.currentTimeMillis());
        }finally {
            lock.unlock();
        }
    }
}
