package com.fzy.thread_communication;

/**
 * 当执行wait()方法的时候，对象锁释放;
 * 当执行notify()方法的时候，对象锁不释放
 *
 * Created by fuzhongyu on 2017/7/7.
 */
public class LockRelease {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 线程a和线程b都打印出了wait begin， 说明在执行wait的时候释放了对象锁
     */
    public static void test1(){

        Object obj=new Object();
        new Thread("a"){
            @Override
            public void run(){
                new LocRelService1().service(obj);
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                new LocRelService1().service(obj);
            }
        }.start();
    }

    /**
     * 线程a和线程b同步执行了，说明notify并没有释放对象锁
     */
    public static void test2(){

        Object obj=new Object();
        new Thread("a"){
            @Override
            public void run(){
                new LocRelService2().service(obj);
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                new LocRelService2().service(obj);
            }
        }.start();
    }
}

class LocRelService1{

    public void service(Object obj){
        try {
            synchronized (obj){
                System.out.println("---begin wait---");
                obj.wait();
                System.out.println("---end wait---");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class LocRelService2{

    public void service(Object obj){
        try {
            synchronized (obj){
                System.out.println("=== begin notify===");
                obj.notify();
                Thread.sleep(2000);
                System.out.println("=== end notify ===");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
