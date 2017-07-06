package com.fzy.thread_synchronized;

/**
 *  用同步块解决调用同一个对象中两个同步方法长时间等待问题
 *
 * Created by fuzhongyu on 2017/7/6.
 */
public class SyncForeverWait {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1(){

        ForService1 forService1=new ForService1();

        new Thread(){
            @Override
            public void run(){
                forService1.service();
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                forService1.serv();
            }
        }.start();
    }

    /**
     * 如果同一个对象中的两个方法没必要同步，则可以使用同步块使线程占用不同对象的对象锁，提高执行效率
     */
    public static void test2(){

        ForService2 forService2=new ForService2();

        new Thread(){
            @Override
            public void run(){
                forService2.service();
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                forService2.serv();
            }
        }.start();
    }
}

class ForService1{

    public synchronized void service(){
        System.out.println("---- service1  begin  -----");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---- service1  end  -----");
    }

    public synchronized void serv(){
        System.out.println("---- service2  begin  -----");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---- service2  end  -----");
    }
}


class ForService2{

    public void service(){
        synchronized (new Object()){
            System.out.println("---- service1  begin  -----");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---- service1  end  -----");
        }

    }

    public void serv(){
        synchronized (new Object()){
            System.out.println("---- service2  begin  -----");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---- service2  end  -----");
        }

    }
}
