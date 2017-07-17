package com.fzy.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用lock实现生产者/消费者
 *
 * Created by fuzhongyu on 2017/7/15.
 */
public class ProCusLock {

    public static void main(String[] args) {
        test1();
    }


    public static void test1(){
        PrCuLoService1 prCuLoService1=new PrCuLoService1();

        new Thread("a"){
            @Override
            public void run(){
                while (true){
                    prCuLoService1.set();
                }
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                while (true){
                    prCuLoService1.get();
                }
            }
        }.start();
    }
}

class PrCuLoService1{

    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();
    private List<String> list=new ArrayList<>();

    public void set(){
        try {
            lock.lock();
            System.out.println("---set 获得了锁----");
            while (list.size()>0){
                condition.await();
            }
            System.out.println("--生产者生产---");
            list.add(System.currentTimeMillis()+"_"+System.nanoTime());
            condition.signalAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void get(){
        try {
            lock.lock();
            System.out.println("====get 获得了锁====");
            while (list.size()==0){
                condition.await();
            }
            System.out.println("===消费者消费====");
            list.clear();
            condition.signalAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
