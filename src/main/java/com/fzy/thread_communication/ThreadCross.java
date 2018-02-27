package com.fzy.thread_communication;

/**
 * 等待/通知，线程交叉备份
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class ThreadCross {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){

        ThrCroService thrCroService=new ThrCroService();

        new Thread("a"){
            @Override
            public void run(){
                while (true){
                    thrCroService.service1();
                }

            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                while (true){
                    thrCroService.service2();
                }
            }
        }.start();
    }

}

class ThrCroService{

    volatile private boolean flag=false;

    synchronized public void service1(){
        try {
            while (flag){
                //释放当前对象的锁
                wait();
            }
            Thread.sleep(1000);
            System.out.println("service1");
            flag=true;
            notifyAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    synchronized public void service2(){
        try {
            while (!flag){
                wait();
            }
            Thread.sleep(1000);
            System.out.println("service2");
            flag=false;
            notifyAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
