package com.fzy.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  ReentrantLock类具有完全互斥排他的效果，即同一时间只有一个线程在执行，虽然这样做能保证线程安全性，但效率十分低下。
 *
 *  ReentrantReadWriteLock类提供了读写锁，在某些不需要操作实例变量的方法中，完全可以使用读写锁来提升方法的运行效率
 *
 *      读锁（共享锁）：多个读锁之间不互斥，在多个线程进行读取操作的时候可以获取读锁
 *      写锁（排他锁）：写锁和写锁互斥，在多个线程进行写入的时候，只有获得了写锁，才能写入。
 *
 *  结论：读写、写读、写写 都是互斥的；而 读读 是非互斥的，即异步的
 *
 * Created by fuzhongyu on 2017/7/18.
 */
public class ReentrantReadWriteLockMethod {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    /**
     * 读读共享
     *
     * 同时输出，没有时间间隔
     */
    public static void test1(){
        ReenReWrLoService1 reenReWrLoService1=new ReenReWrLoService1();
        new Thread("a"){
            @Override
            public void run(){
                reenReWrLoService1.read();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                reenReWrLoService1.read();
            }
        }.start();
    }

    /**
     * 写写互斥
     *
     * 输出有时间间隔
     */
    public static void test2(){
        ReenReWrLoService1 reenReWrLoService1=new ReenReWrLoService1();
        new Thread("a"){
            @Override
            public void run(){
                reenReWrLoService1.write();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                reenReWrLoService1.write();
            }
        }.start();
    }

    /**
     * 读写互斥
     *
     * 输出有1秒以上时间间隔
     */
    public static void test3(){
        ReenReWrLoService1 reenReWrLoService1=new ReenReWrLoService1();

        new Thread("a"){
            @Override
            public void run(){
                reenReWrLoService1.read();
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
                reenReWrLoService1.write();
            }
        }.start();
    }

    /**
     * 写读互斥
     *
     * 输出有1秒以上时间间隔
     */
    public static void test4(){

        ReenReWrLoService1 reenReWrLoService1=new ReenReWrLoService1();

        new Thread("a"){
            @Override
            public void run(){
                reenReWrLoService1.write();
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
                reenReWrLoService1.read();
            }
        }.start();
    }

}

class ReenReWrLoService1{

    private ReentrantReadWriteLock lock=new ReentrantReadWriteLock();

    public void read(){
        try {
            lock.readLock().lock();
            System.out.println("获得读锁 "+Thread.currentThread().getName()+" "+System.currentTimeMillis());
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }

    public void write(){
        try {
            lock.writeLock().lock();
            System.out.println("获得写锁 "+Thread.currentThread().getName()+" "+System.currentTimeMillis());
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }
}
