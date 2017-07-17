package com.fzy.thread_communication.ThreadJoin;


/**
 * join(long)和sleep(long)的区别
 *
 *   join会释放当前线程的锁（注：当前线程对象的锁）
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class JoinSleepCompare {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 一个线程占用着JoSlService1的对象锁,用时6秒
     */
    public static void test1(){

        JoSlService1 joSlService1=new JoSlService1();

        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                try {
                    System.out.println("---");
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thr1.start();

        new Thread("b"){
            @Override
            public void run(){
                joSlService1.service1(thr1);
            }
        }.start();

        new Thread("c"){
            @Override
            public void run(){
                joSlService1.service1(thr1);
            }
        }.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("用时："+(JoSlTi.endTime-JoSlTi.beginTime));


    }

    /**
     * 在用join的时候，会释放该线程的锁，用时且thr1线程死亡的时候wait状态结束，用时2秒
     */
    public static void test2(){

        JoSlService2 joSlService2=new JoSlService2();

        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                try {
                    System.out.println("----");
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thr1.start();

        new Thread("b"){
            @Override
            public void run(){
                joSlService2.service2(thr1);
            }
        }.start();

        new Thread("c"){
            @Override
            public void run(){
                joSlService2.service2(thr1);
            }
        }.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("用时："+(JoSlTi.endTime-JoSlTi.beginTime));

    }

}

class JoSlTi{
    public static long beginTime=0;
    public static long endTime=0;
}

class JoSlService1 extends Thread{

    public void service1(Thread thread){

        synchronized (thread){
            if(JoSlTi.beginTime==0){
                JoSlTi.beginTime=System.currentTimeMillis();
            }else if(JoSlTi.beginTime>System.currentTimeMillis()){
                JoSlTi.beginTime=System.currentTimeMillis();
            }

            System.out.println("--service1---");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(JoSlTi.endTime==0){
                JoSlTi.endTime=System.currentTimeMillis();
            }else if(JoSlTi.endTime<System.currentTimeMillis()){
                JoSlTi.endTime=System.currentTimeMillis();
            }
        }
    }

}


class JoSlService2 extends Thread{

     public void service2(Thread thread){

        synchronized (thread){
            if(JoSlTi.beginTime==0){
                JoSlTi.beginTime=System.currentTimeMillis();
            }else if(JoSlTi.beginTime>System.currentTimeMillis()){
                JoSlTi.beginTime=System.currentTimeMillis();
            }

            System.out.println("--service1---");
            try {
                thread.join(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(JoSlTi.endTime==0){
                JoSlTi.endTime=System.currentTimeMillis();
            }else if(JoSlTi.endTime<System.currentTimeMillis()){
                JoSlTi.endTime=System.currentTimeMillis();
            }
        }

    }

}

