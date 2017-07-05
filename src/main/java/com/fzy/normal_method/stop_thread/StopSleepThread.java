package com.fzy.normal_method.stop_thread;

/**
 * 停止沉睡中的线程，捕获中断异常后会清除线程的中断状态
 * Created by fuzhongyu on 2017/6/30.
 */
public class StopSleepThread {

    public static void main(String[] args) {
        test1();
    }


    public static void test1(){

        try {
            StoSleThr1 thr=new StoSleThr1();
            thr.start();
            Thread.sleep(1000);
            thr.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

class StoSleThr1 extends Thread{

    @Override
    public void run(){
        try {
            System.out.println("run begin");
            Thread.sleep(2000);
            System.out.println("run end");
        } catch (InterruptedException e) {
            //会清除线程的中断状态
            System.out.println("在沉睡中的被停止，进入catch!当前线程停止状态："+this.isInterrupted());  //false
            e.printStackTrace();
        }

    }
}
