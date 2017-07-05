package com.fzy.normal_method;

/**
 * yield() 放弃当前cpu资源，让其他任务去执行
 *
 * Created by fuzhongyu on 2017/7/4.
 */
public class Yield {

    public static void main(String[] args) {

        /**
         * 由于test1需要让给其他资源执行，所以会导致执行时间加长
         */
        test1();
        test2();
    }



    public static void test1(){
        YeThr1 thr1=new YeThr1();
        thr1.start();
    }

    public static void test2(){
        YeThr2 thr2=new YeThr2();
        thr2.start();
    }


}

class YeThr1 extends Thread{

    @Override
    public void run(){
        long beginTime=System.currentTimeMillis();
        for (int i=0;i<5000000;i++){
            Thread.yield();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("thr1用时："+(endTime-beginTime)+"毫秒");
    }
}

class YeThr2 extends Thread{

    @Override
    public void run(){
        long beginTime=System.currentTimeMillis();
        for (int i=0;i<5000000;i++){
        }
        long endTime=System.currentTimeMillis();
        System.out.println("thr2用时："+(endTime-beginTime)+"毫秒");
    }
}
