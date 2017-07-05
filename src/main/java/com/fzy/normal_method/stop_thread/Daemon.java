package com.fzy.normal_method.stop_thread;

/**
 * 守护线程
 *
 * 守护线程是一种特殊的线程，当进程中不存在非守护线程的时候，则守护线程自动销毁，最典型的守护线程如GC
 *
 * Created by fuzhongyu on 2017/7/4.
 */
public class Daemon {

    public static void main(String[] args) {
        test1();

    }

    public static void test1(){
        try {
            DeaThr1 thr1=new DeaThr1();
            //把该线程设置为守护线程
            thr1.setDaemon(true);
            thr1.start();
            Thread.sleep(3000);
            System.out.println("线程执行完毕，守护线程也结束运行");
        }catch (InterruptedException e){
            e.printStackTrace();
        }



    }


}


class DeaThr1 extends Thread{

    private int i=0;
    @Override
    public void run(){
        try {
            while (true){
                i++;
                System.out.println("i="+i);
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}