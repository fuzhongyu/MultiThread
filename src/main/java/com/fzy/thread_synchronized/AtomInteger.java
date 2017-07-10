package com.fzy.thread_synchronized;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用atomicInteger 进行i++的原子性操作
 *
 * Created by fuzhongyu on 2017/7/6.
 */
public class AtomInteger {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 使用原子类，累加到50000
     */
    public static void test1(){
        AtoIntThr1 atoIntThr1=new AtoIntThr1();

        for (int i=0;i<5;i++){
            new Thread(atoIntThr1).start();
        }

    }

    /**
     * 非原子类执行，会出现未累加到50000，中间存在脏读现象
     */
    public static void test2(){
        AtoIntThr2 atoIntThr2=new AtoIntThr2();

        for (int i=0;i<5;i++){
            new Thread(atoIntThr2).start();
        }

    }
}

class AtoIntThr1 extends Thread{

    private AtomicInteger count=new AtomicInteger(0);

    @Override
    public void run(){
        for (int i=0;i<10000;i++){
            System.out.println(count.incrementAndGet());
        }
    }
}

class AtoIntThr2 extends Thread{

    private int count=0;

    @Override
    public void run(){
        for (int i=0;i<10000;i++){
            count++;
            System.out.println(count);
        }
    }
}
