package com.fzy.normal_method;


/**
 * 判断当前线程是否处于活动状态
 * Created by fuzhongyu on 2017/6/27.
 */
public class IsAlive {


    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1(){

        AlThread1 thr1=new AlThread1();
        System.out.println("begin isAlive="+thr1.isAlive());
        thr1.start();
        //end的时候线程是否处于活动状态无法确定： 如果thr还未执行完毕，则打印true ,如果执行完毕了,则打印false
        System.out.println("end isAlive="+thr1.isAlive());
    }


    public static void test2(){
        AlThread2 thr2=new AlThread2();
        Thread thread=new Thread(thr2);
        System.out.println("begin isAlive="+thread.isAlive());
        thread.setName("A");
        thread.start();
//        thr2.start();
        System.out.println("end isAlive="+thread.isAlive());
    }



}



class AlThread1 extends Thread{

    @Override
    public void run(){
        System.out.println("run="+this.isAlive());
    }
}

class AlThread2 extends Thread{

    public AlThread2(){
        System.out.println("------Thr2  begin------");
        System.out.println("Thread.currentThread().getName()="+Thread.currentThread().getName());
        System.out.println("Thread.currentThread().isAlive()="+Thread.currentThread().isAlive());
        System.out.println("this.getName()="+this.getName());
        System.out.println("this.isAlive()="+this.isAlive());
        System.out.println("------Thr2  end------");
    }


    @Override
    public void run(){
        System.out.println("========run  begin=========");
        System.out.println("Thread.currentThread().getName()="+Thread.currentThread().getName());
        System.out.println("Thread.currentThread().isAlive()="+Thread.currentThread().isAlive());
        System.out.println("this.getName()="+this.getName());
        System.out.println("this.isAlive()="+this.isAlive());
        System.out.println("========run  end=========");
    }

}
