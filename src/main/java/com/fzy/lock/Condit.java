package com.fzy.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition实现等待/通知
 *
 *  注： （1）在一个Lock中可以创建多个Condition（对象监视器）,从而可以选择性的进行线程通知,而notify中被通知的线程是有jvm随机选择的
 *      （2）synchronized相当于整个Lock中只有一个单一的Condition对象，所有线程都注册在他的一个对象中，线程开始notifAll()时，需要通知所有的wait线程，没有选择权
 *
 * Created by fuzhongyu on 2017/7/15.
 */
public class Condit {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
        test6();
    }

    /**
     * 会报错，因为在condition.await()方法调用之前必须要获得同步监视器
     */
    public static void test1(){
        CondiService1 condiService1=new CondiService1();
        new Thread("a"){
            @Override
            public void run(){
                condiService1.await1();
            }
        }.start();
    }

    /**
     * 使用lock获得对象同步监视器，但控制台只打印出begin，因为condition的await()方法是线程进入等待状态
     */
    public static void test2(){
        CondiService2 condiService2=new CondiService2();
        new Thread("a"){
            @Override
            public void run(){
                condiService2.await1();
            }
        }.start();
    }

    /**
     * 用lock2对lock1创建的condition加锁，同样会出现为获得对象同步监视器的错误
     */
    public static void test3(){
        CondiService2 condiService2=new CondiService2();
        new Thread("a"){
            @Override
            public void run(){
                condiService2.await2();
            }
        }.start();
    }


    /**
     * 实现等待/通知 模式    等待:condition.await()   通知：condition.signal()
     */
    public static void test4(){
        CondiService3 condiService3=new CondiService3();
        new Thread("a"){
            @Override
            public void run(){
                condiService3.await();
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("b"){
            @Override
            public void run(){
                condiService3.signal();
            }
        }.start();
    }

    /**
     * 使用同一个condition随机唤醒，其中一个
     */
    public static void test5(){
        CondiService4 condiService4=new CondiService4();
        new Thread("a"){
            @Override
            public void run(){
                condiService4.await1();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                condiService4.await2();
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("c"){
            @Override
            public void run(){
                condiService4.signal1();
            }
        }.start();
    }

    /**
     * 使用不同condition可以选择性唤醒需要唤醒的线程
     */
    public static void test6(){
        CondiService4 condiService4=new CondiService4();
        new Thread("a"){
            @Override
            public void run(){
                condiService4.await1();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                condiService4.await3();
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("c"){
            @Override
            public void run(){
                //唤醒await1
                condiService4.signal1();
                //唤醒await3
//                condiService4.signal2();
            }
        }.start();
    }



}

class CondiService1{

    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void await1(){
        try {
            condition.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class CondiService2{
    private Lock lock1=new ReentrantLock();
    private Lock lock2=new ReentrantLock();
    private Condition condition=lock1.newCondition();

    public void await1(){
        try {
            lock1.lock();
            System.out.println("begin ");
            condition.await();
            System.out.println("end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock1.unlock();
        }
    }

    public void await2(){
        try {
            lock2.lock();
            System.out.println("begin");
            condition.await();
            System.out.println("end ");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock2.unlock();
        }
    }
}

class CondiService3{
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void await(){
        try {
            lock.lock();
            System.out.println("-begin");
            condition.await();
            System.out.println("-end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void signal(){
        try {
            lock.lock();
            System.out.println("signal的时间是 "+System.currentTimeMillis());
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

}

class CondiService4{
    private Lock lock=new ReentrantLock();
    private Condition condition1=lock.newCondition();
    private Condition condition2=lock.newCondition();

    public void await1(){
        try {
            lock.lock();
            System.out.println("--await1 begin");
            condition1.await();
            System.out.println("--await1 end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void await2(){
        try {
            lock.lock();
            System.out.println("--await2 begin");
            condition1.await();
            System.out.println("--await2 end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void await3(){
        try {
            lock.lock();
            System.out.println("--await3 begin");
            condition2.await();
            System.out.println("--await3 end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }



    public void signal1(){
        try {
            lock.lock();
            System.out.println(" signal的时间是"+System.currentTimeMillis());
            //随机condition1中的一个线程
            condition1.signal();
            //唤醒condition1中的所有线程
//            condition1.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void signal2(){
        try {
            lock.lock();
            System.out.println("signal的时间是:"+System.currentTimeMillis());
            //随机condition2中的一个线程
            condition2.signal();
            //唤醒condition2中的所有线程
//            condition2.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
