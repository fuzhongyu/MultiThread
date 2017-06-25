package com.fzy.normal_method;

import org.junit.Test;

/**
 * 线程数据共享与不共享
 * Created by fuzhongyu on 2017/5/1.
 */
public class TheadDataShare {

    //不共享数据情况
    @Test
    public void test1(){
        ThreadNotShare threadNotShare_A=new ThreadNotShare("A");
        ThreadNotShare threadNotShare_B=new ThreadNotShare("B");
        threadNotShare_A.start();
        threadNotShare_B.start();
    }

    //共享数据情况
    @Test
    public void test2(){
        ThreadShare threadShare=new ThreadShare();
        Thread thread_C=new Thread(threadShare,"C");
        Thread thread_D=new Thread(threadShare,"D");
        Thread thread_E=new Thread(threadShare,"E");
        thread_C.start();
        thread_D.start();
        thread_E.start();
    }

    //共享数据情况(加锁)
    @Test
    public void test3(){
        ThreadShareSyn threadShare=new ThreadShareSyn();
        Thread thread_C=new Thread(threadShare,"F");
        Thread thread_D=new Thread(threadShare,"G");
        Thread thread_E=new Thread(threadShare,"H");
        thread_C.start();
        thread_D.start();
        thread_E.start();
    }


    private class ThreadNotShare extends Thread{
        private int count=5;

        public ThreadNotShare(String name){
            this.setName(name);
        }

        @Override
        public void run(){
            while (count>0){
                count--;
                System.out.println("thread_not_share:由"+this.currentThread().getName()+"计算,count="+count);
            }
        }
    }

    private class ThreadShare extends Thread{
        private int count=5;

        @Override
        public void run(){
            count--;
            System.out.println("thread_share:由"+this.currentThread().getName()+"计算,count="+count);
        }
    }


    private class ThreadShareSyn extends Thread {
        private int count = 5;

        @Override
        public synchronized void run() {
            count--;
            System.out.println("thread_share:由" + this.currentThread().getName() + "计算,count=" + count);
        }
    }
}