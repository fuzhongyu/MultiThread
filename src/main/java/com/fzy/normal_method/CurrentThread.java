package com.fzy.normal_method;



/**
 * 获取当前线程方法（不建议使用此方法，强制让线程停止会使一些清理工作或同步工作不能完成）
 *
 * 当stop的时候，线程会抛出java.lang.ThreadDeath异常，但通常情况下此异常不需要显示的捕获
 *
 * Created by fuzhongyu on 2017/6/27.
 */
public class CurrentThread {

    public static void main(String[] args) {
        test1();
//        test2();
//        test3();
    }



    /**
     *  比较 this.getName() 和 Thread.currentThread().getName() 的区别
     */
    public static void  test1(){
        CuThread1 thr1=new CuThread1();
        thr1.setName("A");
        thr1.start();
    }

    public static void  test2(){
        CuThread2 thr2=new CuThread2();

        CuThread1 thr1=new CuThread1();
        Thread thread=new Thread(thr1);
        System.out.println("thread info------>"+thread.getName()+"   "+thread.getId());
        thread.start();
    }

    public static void  test3(){
        CuThread1 thr1=new CuThread1();
        Thread thread=new Thread(thr1);
        thread.setName("A");
        thread.start();
    }


}

 class CuThread1 extends Thread{

    public CuThread1(){
        System.out.println("------Thr1  begin------");
        System.out.println("Thread.currentThread().getName()="+Thread.currentThread().getName());
        System.out.println("this.getName()="+this.getName()+"   this.getId()="+this.getId());
        System.out.println("------Thr1  end------");
    }


    @Override
    public void run(){
        System.out.println("========run  begin=========");
        System.out.println("Thread.currentThread().getName()="+Thread.currentThread().getName());
        System.out.println("this.getName()="+this.getName()+"   this.getId()="+this.getId());
        System.out.println("========run  end=========");
    }

}


class CuThread2 extends Thread{
    public CuThread2(){
        System.out.println("this.getName()="+this.getName());
    }
}