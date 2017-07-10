package com.fzy.thread_communication;

import java.util.ArrayList;
import java.util.List;

/**
 *  等待wait 的条件发生变化
 *
 * Created by fuzhongyu on 2017/7/7.
 */
public class WaitCondition {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     *  这种方式会抛异常，因为有两个实现删除的线程，在唤醒的时候其中一个线程先获得了对象锁向下执行，
     *  执行完毕后另一个线程再获得对象锁 也向下执行语句，这时候list中已经没有元素就导致了越界问题
     */
    public  static void test1(){

        List<Object> list=new ArrayList();

        new Thread("a"){
            @Override
            public void run(){
                WaConSubtract1 waConSubtract1=new WaConSubtract1(list);
                waConSubtract1.subtractVal();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                WaConSubtract1 waConSubtract1=new WaConSubtract1(list);
                waConSubtract1.subtractVal();
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
                WaConAdd1 waConAdd1=new WaConAdd1(list);
                waConAdd1.addVal("ce");
            }
        }.start();

    }


    /**
     *  这种方式通过while再次判断，避免了上述问题
     */
    public  static void test2(){

        List<Object> list=new ArrayList();

        new Thread("a"){
            @Override
            public void run(){
                WaConSubtract2 waConSubtract2=new WaConSubtract2(list);
                waConSubtract2.subtractVal();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                WaConSubtract2 waConSubtract2=new WaConSubtract2(list);
                waConSubtract2.subtractVal();
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
                WaConAdd1 waConAdd1=new WaConAdd1(list);
                waConAdd1.addVal("ce");
            }
        }.start();

    }
}

class WaConAdd1{

    private List<Object> list;

    public WaConAdd1(List list){
        this.list=list;
    }

    public void addVal(String val){
        synchronized (list){
            list.add(val);
            list.notifyAll();
        }

    }
}

class WaConSubtract1{

    private List<Object> list;

    public WaConSubtract1(List list){
        this.list=list;
    }

    public void subtractVal(){
        try {
            synchronized (list){
                if(list.size()==0){
                    System.out.println("---"+ Thread.currentThread().getName()+" wait begin---");
                    list.wait();
                    Thread.sleep(1000);
                    System.out.println("----"+ Thread.currentThread().getName()+" wait end----");
                }
                list.remove(0);
                System.out.println(Thread.currentThread().getName()+" list size="+list.size());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class WaConSubtract2{

    private List<Object> list;

    public WaConSubtract2(List list){
        this.list=list;
    }

    public void subtractVal(){
        try {
            synchronized (list){
                while (list.size()==0){
                    System.out.println("---"+ Thread.currentThread().getName()+" wait begin---");
                    list.wait();
                    Thread.sleep(1000);
                    System.out.println("----"+ Thread.currentThread().getName()+" wait end----");
                }
                list.remove(0);
                System.out.println(Thread.currentThread().getName()+"  list size="+list.size());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
