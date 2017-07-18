package com.fzy.supplement;


/**
 * 线程异常处理传递(将若干线程异常处理方式放在一起运行出现的情况)
 *
 *   异常处理顺序： 线程对象(异常不会往下传递)----->线程组（通过 super.uncaughtException(t,e);可往下传递）------>线程类
 *
 * Created by fuzhongyu on 2017/7/17.
 */
public class ThrExcepTransmit {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
        test5();
    }

    /**
     *  线程对象会先捕获到异常，且线程类不在处理
     */
    public static void test1(){
        ThrExTraThread1 thr1=new ThrExTraThread1();
        //对象
        thr1.setUncaughtExceptionHandler(new ObjectUncaughtExceptionHandler());
        //类
        ThrExTraThread1.setDefaultUncaughtExceptionHandler(new StateUncaughtExceptionHandler());
        thr1.start();

    }


    /**
     * 线程类处理异常
     */
    public static void test2(){
        ThrExTraThread1 thr1=new ThrExTraThread1();
        ThrExTraThread1.setDefaultUncaughtExceptionHandler(new StateUncaughtExceptionHandler());
        thr1.start();
    }

    /**
     * 线程对象会先捕获到异常，且线程组不在处理
     */
    public static void test3(){
        ThrExTraThreadGroup group=new ThrExTraThreadGroup("my group");
        ThrExTraThread1 thr1=new ThrExTraThread1(group,"my thread");

        thr1.setUncaughtExceptionHandler(new ObjectUncaughtExceptionHandler());

        ThrExTraThread1.setDefaultUncaughtExceptionHandler(new StateUncaughtExceptionHandler());

        thr1.start();
    }

    /**
     * 线程组和线程类做异常处理，线程组会先捕获异常，当线程组中有 super.uncaughtException(t,e);的时候，线程类会继续处理异常，没有这句话则线程类不做处理
     */
    public static void test4(){
        ThrExTraThreadGroup group=new ThrExTraThreadGroup("my group");
        ThrExTraThread1 thr1=new ThrExTraThread1(group,"my thread");

        ThrExTraThread1.setDefaultUncaughtExceptionHandler(new StateUncaughtExceptionHandler());

        thr1.start();
    }

    /**
     * 线程组做异常处理
     */
    public static void test5(){
        ThrExTraThreadGroup group=new ThrExTraThreadGroup("my group");
        ThrExTraThread1 thr1=new ThrExTraThread1(group,"my thread");
        thr1.start();
    }
}

class ThrExTraThread1 extends Thread{

    private String num="a";

    public ThrExTraThread1(){}

    public ThrExTraThread1(ThreadGroup group,String name){
        super(group,name);
    }

    @Override
    public void run(){
        int numInt=Integer.parseInt(num);
        System.out.println("线程在打印："+(numInt+1));
    }
}

class ThrExTraThreadGroup extends ThreadGroup{

    public ThrExTraThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t,Throwable e){
        super.uncaughtException(t,e);
        System.out.println("线程组的异常处理");
        e.printStackTrace();
    }
}

class ObjectUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("对象异常处理");
        e.printStackTrace();
    }
}

class StateUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("静态的异常处理");
        e.printStackTrace();
    }
}
