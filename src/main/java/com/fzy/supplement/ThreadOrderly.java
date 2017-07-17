package com.fzy.supplement;

/**
 * 使线程具有有序性
 *
 * Created by fuzhongyu on 2017/7/17.
 */
public class ThreadOrderly {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1(){
        Object lock=new Object();
        new ThrOrdThread1(lock,"A",1).start();
        new ThrOrdThread1(lock,"B",2).start();
        new ThrOrdThread1(lock,"C",0).start();
    }

    public static void test2(){
        Object lock=new Object();
        new ThrOrdThread2("A",3,lock).start();
        new ThrOrdThread2("B",1,lock).start();
        new ThrOrdThread2("C",2,lock).start();
    }
}

class ThrOrdThread1 extends Thread{

    private Object lock;
    private String showChar;
    private int showNumposition;
    private int printCount=0;  //统计打印了几个字母
    volatile private static int addNumber=1;

    public ThrOrdThread1(Object lock,String showChar,int showNumposition){
        this.lock=lock;
        this.showChar=showChar;
        this.showNumposition=showNumposition;
    }

    @Override
    public void run(){
        try {
            synchronized (lock){
                while (true){
                    if(addNumber%3==showNumposition){
                        System.out.println("threadName:"+Thread.currentThread().getName()+" runCount="+addNumber+"  "+showChar);
                        lock.notifyAll();
                        addNumber++;
                        printCount++;
                        if(printCount==3){
                            break;
                        }
                    }else {
                        lock.wait();
                    }
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class ThrOrdThread2 extends Thread{

    private Object lock;
    private int position;
    private String name;
    volatile private static int curPosition=0;

    public ThrOrdThread2(String name,int position,Object lock){
        this.name=name;
        this.position=position;
        this.lock=lock;
    }

    @Override
    public void run(){
        try {
            while (true){
                synchronized (lock){
                    if(position%3==curPosition){
                        System.out.println(Thread.currentThread().getName()+":"+name);
                        curPosition=(curPosition+1)%3;
                        Thread.sleep(1000);
                        lock.notifyAll();
                    }else {
                        lock.wait();
                    }
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}