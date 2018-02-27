package com.fzy.thread_synchronized;

/**
 * synchronized可以使多个线程访问同一个资源具有同步性，而且它还具有将线程工作内存中的私有变量与共有变量同步的功能
 *
 * Created by fuzhongyu on 2017/7/6.
 */
public class SyncVolatile {

    public static void main(String[] args) {
        test1();
//        test2();
    }

    /**
     * 在server  运行模式下不能停止
     */
   public static void test1(){

       SynVolService1 service1=new SynVolService1();

       new Thread(){
           @Override
           public void run(){
               service1.service();
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
               service1.stopSer();
           }
       }.start();
   }


    /**
     * 在server 模式下能停止,sychronize会将线程工作内存中的私有变量和共有堆栈变量同步
     */
    public static void test2(){

        SynVolService2 service2=new SynVolService2();

        new Thread(){
            @Override
            public void run(){
                service2.service();
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
                service2.stopSer();
            }
        }.start();
    }
}





class SynVolService1{

    private boolean isCOntinueRun=true;

    public void service(){
        while (isCOntinueRun){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--- service1 execute ----");
        }
        System.out.println("--- service1 线程停止---");
    }

    public void stopSer(){
        isCOntinueRun=false;
    }
}


class SynVolService2{

    private boolean isCOntinueRun=true;
    public void service(){
        while (isCOntinueRun){
            synchronized (new Object()){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--- service2 execute ----");
            }
        }
        System.out.println("--- service2 线程停止---");
    }

    public void stopSer(){
        isCOntinueRun=false;
    }
}
