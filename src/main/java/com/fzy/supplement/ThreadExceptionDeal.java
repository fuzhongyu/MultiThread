package com.fzy.supplement;

/**
 * 线程中异常处理
 *
 * Created by fuzhongyu on 2017/7/17.
 */
public class ThreadExceptionDeal {

    public static void main(String[] args) {
        test1();
//        test2();
    }

    /**
     * 使用setUncaughtExceptionHandler类对发生的异常进行有效处理
     */
    public static void test1(){

        ThrExDeThread thrExDeThread1=new ThrExDeThread();
        thrExDeThread1.setName("a");
        thrExDeThread1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("线程:"+t.getName()+"出现了异常");
                e.printStackTrace();
            }
        });

        thrExDeThread1.start();

        try {
            Thread.sleep(1000);
            System.out.println("--------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThrExDeThread thrExDeThread2=new ThrExDeThread();
        thrExDeThread2.start();
    }


    /**
     * 使用setDefaultUncaughtExceptionHandler类对为指定线程类的所有线程对象设置默认的异常处理器
     */
    public static void test2(){

        ThrExDeThread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("线程:"+t.getName()+"出现了异常");
                e.printStackTrace();
            }
        });

        try {
            Thread.sleep(1000);
            System.out.println("--------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThrExDeThread thrExDeThread1=new ThrExDeThread();
        thrExDeThread1.start();


        ThrExDeThread thrExDeThread2=new ThrExDeThread();
        thrExDeThread2.start();
    }
}

/**
 * 异常线程
 */
class ThrExDeThread extends Thread{

    @Override
    public void run(){
        String username=null;
        System.out.println(username.hashCode());
    }
}
