package com.fzy.thread_communication;

/**
 * 当线程处于wait状态的时候，调用对象的中断方法会出现中断异常
 *
 * Created by fuzhongyu on 2017/7/7.
 */
public class WaitInterrupt {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){

        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                new WaInService1().service(new Object());
            }
        };
        thr1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //验证中断异常
        thr1.interrupt();
    }
}

class WaInService1{

    public void service(Object obj){
        try {
            synchronized (obj){
                System.out.println("---begin wait---");
                obj.wait();
                System.out.println("---end wait---");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            System.out.println("线程出现了异常");
        }
    }
}
