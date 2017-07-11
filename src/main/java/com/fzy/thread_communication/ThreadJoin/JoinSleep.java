package com.fzy.thread_communication.ThreadJoin;


/**
 * join(long)和sleep(long)的区别
 *
 *   join会释放当前线程的锁（注：当前线程对象的锁）
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class JoinSleep {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1(){

        JoSlService1 joSlService1=new JoSlService1();

        new Thread("a"){
            @Override
            public void run(){
                joSlService1.service1();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                joSlService1.service2();
            }
        }.start();

    }

    public static void test2(){

        JoSlService1 joSlService1=new JoSlService1();

      new Thread("a"){
            @Override
            public void run(){
                joSlService1.service1();
            }
        }.start();



        new Thread("b"){
            @Override
            public void run(){
                joSlService1.service2();
            }
        }.start();


    }
}

class JoSlService1 extends Thread{


    synchronized public void service1(){
        System.out.println("--service1---");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void service2(){
        System.out.println("====service2===");
    }
}

