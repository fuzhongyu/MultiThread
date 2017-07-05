package com.fzy.normal_method;

/**
 * 线程休眠方法
 * Created by fuzhongyu on 2017/6/28.
 */
public class Sleep {


    public static void main(String[] args) {
       test1();
    }


    public static void test1(){
        SlThread1 thr1=new SlThread1();
        System.out.println("begin="+System.currentTimeMillis());
        thr1.start();
        System.out.println("end="+System.currentTimeMillis());
    }





}

class SlThread1 extends Thread{

    @Override
    public void run(){

        try {
            System.out.println("====run thread begin="+System.currentTimeMillis());
            Thread.sleep(2000);
            System.out.println("====run thread end="+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



