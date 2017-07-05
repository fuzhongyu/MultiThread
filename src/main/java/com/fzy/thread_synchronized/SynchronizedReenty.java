package com.fzy.thread_synchronized;

/**
 * synchronized锁重入
 *
 *   在使用synchronized时，当一个线程得到对象锁后，再此请求此对象时可以再次得到该对象的锁
 * Created by fuzhongyu on 2017/7/5.
 */
public class SynchronizedReenty {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    //synchronized锁  自己可以再次获取自己的内部锁
    public static void test1(){
        SyThr1 syThr1=new SyThr1();
        syThr1.start();
    }

    // 当存在父子类继承关系的时候，子类可以通过"可重入锁"调用父类的同步方法
    public static void test2(){
        SyThr2 syThr2=new SyThr2();
        syThr2.start();
    }

}

class SyThr1 extends Thread{

    @Override
    public void run(){
        SyReService ser=new SyReService();
        ser.service1();
    }

}

class SyThr2 extends Thread{

    @Override
    public void run(){
        SyReServ serv=new SyReServ();
        serv.service();
    }
}

class SyReService{

    public synchronized void service1(){
        System.out.println("---service1----");
        service2();
    }

    public synchronized void service2(){
        System.out.println("----service2----");
        service3();
    }

    public synchronized void service3(){
        System.out.println("-----service3---");
    }
}

class SyReServ extends SyReService{

    public synchronized void service(){
        System.out.println("---service1----");
        super.service1();
    }

}
