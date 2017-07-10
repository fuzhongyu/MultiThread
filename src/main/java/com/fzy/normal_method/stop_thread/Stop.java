package com.fzy.normal_method.stop_thread;

/**
 * 使用stop()方法，暴力停止线程
 *
 * Created by fuzhongyu on 2017/6/30.
 */
public class Stop {

    public static void main(String[] args) {
        test1();
    }


    public static void test1(){
        try {
            StopThr1 thr=new StopThr1();
            thr.start();
            thr.sleep(1000);
            thr.stop();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}

class StopThr1 extends Thread{

    @Override
    public void run(){
        try {
            for(int i=0;i<5;i++){
                System.out.println("i="+i);
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
