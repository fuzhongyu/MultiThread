package com.fzy.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁和非公平锁 （synchornized获得的是非公平锁）
 *
 *    公平锁：线程获取锁的顺序是按照线程加锁的顺序来分配的，即先来先得
 *    非公平锁：一种获取锁的抢占机制，是随机获得锁的，先来的不一定先得到锁，这可能造成某些线程一直拿不到锁。
 * Created by fuzhongyu on 2017/7/15.
 */
public class FairUnfairLock {

    public static void main(String[] args) {
//        test1();
        tes2();
    }

    /**
     * 公平锁
     */
    public static void test1(){
        FaLoService faLoService=new FaLoService(true);
        Thread[] threads=new Thread[5];
        for (int i=0;i<5;i++){
            threads[i]=new Thread(i+""){
                @Override
                public void run(){
                    System.out.println(Thread.currentThread().getName()+"运行了");
                    faLoService.service1();
                }
            };
        }
        for (Thread thr:threads){
            thr.start();
        }

    }

    /**
     * 非公平锁
     */
    public static void tes2(){
        FaLoService faLoService=new FaLoService(false);
        Thread[] threads=new Thread[5];
        for (int i=0;i<5;i++){
            threads[i]=new Thread(i+""){
                @Override
                public void run(){
                    System.out.println(Thread.currentThread().getName()+"运行了");
                    faLoService.service1();
                }
            };
        }
        for (Thread thr:threads){
            thr.start();
        }
    }

}

class FaLoService{
    private ReentrantLock lock;

    public FaLoService(boolean isFair){
        lock=new ReentrantLock(isFair);
    }

    public void service1(){
        try {
            lock.lock();
            System.out.println("线程"+Thread.currentThread().getName()+" 获得锁");
        }finally {
            lock.unlock();
        }
    }
}
