package com.fzy.thread_synchronized;


/**
 * 锁对象改变
 * Created by fuzhongyu on 2017/7/6.
 */
public class SyncObjChange {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 线程1和线程2需要抢占"a"的锁，所以会出现同步
     */
    public static void test1(){

        ObjService objService=new ObjService();

        new Thread(){
            @Override
            public void run(){
                objService.service("thr1");
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                objService.service("thr2");
            }
        }.start();
    }

    /**
     * 线程1抢占"a"的锁，并改变了str为"b",所以执行到线程2的时候，需要抢占的是"b"的锁，故会异步执行
     */
    public static void test2(){

        ObjService objService=new ObjService();

        new Thread(){
            @Override
            public void run(){
                objService.service("thr1");
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run(){
                objService.service("thr2");
            }
        }.start();
    }
}

class ObjService{

    private String str="a";

    public void service(String name){
        synchronized (str){
            try {
                System.out.println("---"+name+" service begin ---");
                Thread.sleep(500);
                str="b";
                Thread.sleep(2000);

                System.out.println("----"+name+" service end ---");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
