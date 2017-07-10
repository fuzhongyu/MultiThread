package com.fzy.thread_synchronized;


/**
 * 验证volatile是否具备原子性
 *
 *
 * 注： volatile和synchronized比较:
 *     （1）关键字volatile是线程同步的轻量级实现，在性能上优于synchronized,但volatile只能修饰变量，而synchronized能修饰方法，代码块
 *      (2) 多线程访问volatile不会发生阻塞，而synchronized会阻塞
 *      (3) volatile能保证数据的可见性，但不能保证原子性；synchronized可以保证原子性，也可以间接保证可见性，他会将私有内存和公共内存数据同步
 *      (4) volatile解决的是变量在多个线程间的可见性，而synchronized解决的是多个线程访问资源的同步性
 *
 * Created by fuzhongyu on 2017/7/6.
 */
public class VolatileAtomicity {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 会出现count相同的情形，故volatile不具备同步性
     */
    public static void test1(){

        VolAtThr1[] thr1s=new VolAtThr1[10];
        for (int i=0;i<10;i++){
            thr1s[i]=new VolAtThr1();
        }
        for (int i=0;i<10;i++){
            thr1s[i].start();
        }

    }

    /**
     * synchronized同步
     */
    public static void test2(){

        VolAtThr2[] thr2s=new VolAtThr2[10];
        for (int i=0;i<10;i++){
            thr2s[i]=new VolAtThr2();
        }
        for (int i=0;i<10;i++){
            thr2s[i].start();
        }

    }


}

class VolAtThr1 extends Thread{

   volatile public static int count;

    private static void addCount(){
        for (int i=0;i<10;i++){
            count++;
        }
        System.out.println("count="+count);
    }

    @Override
    public void run(){
        addCount();
    }
}

class VolAtThr2 extends Thread{

    public static int count;

    private synchronized static void addCount(){
        for (int i=0;i<10;i++){
            count++;
        }
        System.out.println("count="+count);
    }

    @Override
    public void run(){
        addCount();
    }
}
