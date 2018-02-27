package com.fzy.thread_communication;

/**
 *  wait(long) 方法，等待在某以时间段内是否有线程对锁进行唤醒，如果超过这个时间，则自动唤醒
 *
 * Created by fuzhongyu on 2017/7/7.
 */
public class WaitTime {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 3秒后自动唤醒
     */
    public static void test1(){

        new Thread("a"){
            @Override
            public void run(){
                try {
                    Object obj=new Object();
                    synchronized (obj){
                        System.out.println("--- wait begin ----");
                        //等待3秒唤醒，如果没有通知，则自动唤醒(需要获得obj的锁)
                        obj.wait(3000);
                        System.out.println("--- wait end ---");
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 1秒后有线程去主动唤醒等待的线程
     */
    public static void test2(){

        Object obj=new Object();

        new Thread("a"){
            @Override
            public void run(){
                try {

                    synchronized (obj){
                        System.out.println("--- wait begin ----");
                        obj.wait(3000);
                        System.out.println("--- wait end ---");
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("b"){
            @Override
            public void run(){
                synchronized (obj){
                   obj.notify();
                }

            }
        }.start();
    }
}

