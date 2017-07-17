package com.fzy.supplement;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  线程状态
 *
 *  NEW : 线程实例化后还从未执行start()方法
 *  RUNNABLE: 线程进入运行状态
 *  TERMINATED :线程被销毁时的状态
 *  TIMED_WAITING :代表线程执行了Thread.sleep()方法，呈等待状态
 *  BLOCKED : 线程在等待锁的时候（ ****这里要注意的是只有synchronized这种方式的锁（monitor锁）才会让线程出现BLOCKED状态，等待ReentrantLock则不会***）
 *  WAITING  :线程执行了Object.wait()方法后所处的状态
 *
 * Created by fuzhongyu on 2017/7/16.
 */
public class ThreadState {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
//        test4();
    }

    public static void test1(){
        try {
            Thread thr1=new Thread("a"){
                @Override
                public void run(){
                    System.out.println("---线程进入sleep---");

                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            System.out.println("创建线程还未run的状态："+thr1.getState());
            thr1.start();
            System.out.println("线程run时候的状态："+thr1.getState());

            //让thr1线程充分启动，但还没过sleep时间
            Thread.sleep(100);
            System.out.println("线程sleep时状态："+thr1.getState());
           //thr1运行线程结束
            Thread.sleep(1000);
            System.out.println("th1线程运行结束后状态："+thr1.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void test2(){
        TrStService trStService=new TrStService();
        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                trStService.service1();
            }
        };
        Thread thr2=new Thread("b"){
            @Override
            public void run(){
                trStService.service1();
            }
        };


        try {
            thr1.start();
            //让thr1先启动
            Thread.sleep(210);
            thr2.start();
            //让thr1和thr2启动充分
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thr2等待锁时候的状态："+thr2.getState());
    }

    /**
     * 通过test2和test3比较可以看出，当使用lock的时候并没有出现blocked，
     * 其原因主要是：这里要注意的是只有synchronized这种方式的锁（monitor锁）才会让线程出现BLOCKED状态，等待ReentrantLock则不会
     */
    public static void test3(){
        TrStService trStService=new TrStService();
        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                trStService.service2();
            }
        };
        Thread thr2=new Thread("b"){
            @Override
            public void run(){
                trStService.service2();
            }
        };


        try {
            thr1.start();
            //让thr1先启动
            Thread.sleep(200);
            thr2.start();
            //让thr1和thr2启动充分
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thr2等待锁时候的状态："+thr2.getState());
    }

    public static void test4(){
        TrStService trStService=new TrStService();
        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                trStService.service3();
            }
        };
        thr1.start();

        try {
            //让thr1启动充分
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thr1等待锁时候的状态："+thr1.getState());
    }
}

class TrStService{

    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void service1(){
        try {
            lock.lock();
            Thread.sleep(1100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public synchronized void service2(){
        try {
            Thread.sleep(11000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void service3(){
        try {
            lock.lock();
            condition.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}


