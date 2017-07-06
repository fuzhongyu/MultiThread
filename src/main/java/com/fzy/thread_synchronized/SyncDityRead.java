package com.fzy.thread_synchronized;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程1和线程2执行异步会出现脏读现象
 *
 * Created by fuzhongyu on 2017/7/6.
 */
public class SyncDityRead {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 输出结果为2，因为线程1和线程2是异步执行的，而list是单例，对象锁并不能控制不同对象的synchronized方法，所以会出现脏读现象
     */
    public static void test1(){
        MyOneList list=new MyOneList();

        new Thread(){
            @Override
            public void run(){
                DitService1 dit=new DitService1();
                dit.addData(list,"a");
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                DitService1 dit=new DitService1();
                dit.addData(list,"b");
            }
        }.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(list.getSize());
    }

    /**
     * DitService2控制了list单例对象的添加，防止脏读情况出现
     */
    public static void test2(){
        MyOneList list=new MyOneList();

        new Thread(){
            @Override
            public void run(){
                DitService2 dit=new DitService2();
                dit.addData(list,"a");
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                DitService2 dit=new DitService2();
                dit.addData(list,"b");
            }
        }.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(list.getSize());
    }
}


/**
 * 存在脏读
 */
class DitService1{

    public MyOneList addData(MyOneList list,String data){
        try {
            //控制只list只存一个数据
            if(list.getSize()<1){
                Thread.sleep(1000);  //模拟从远程获取数据
                list.add(data);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return list;
    }

}

/**
 * 控制list添加操作同步执行，避免脏读现象
 */
class DitService2{

    public MyOneList addData(MyOneList list,String data){
        try {
            //此处有可能会出现异步线程同时进入操作的现象，需要加锁控制，防止脏读
            synchronized (list){
                //控制只list只存一个数据
                if(list.getSize()<1){
                    Thread.sleep(1000);  //模拟从远程获取数据
                    list.add(data);
                }
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return list;
    }

}

/**
 *  存储一个元素的list
 */
class MyOneList{

    private List<String> list=new ArrayList<>();


    public synchronized void add(String data){
        list.add(data);
    }

    public synchronized int getSize(){
        return list.size();
    }
}
