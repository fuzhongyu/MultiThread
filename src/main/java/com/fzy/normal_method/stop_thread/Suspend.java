package com.fzy.normal_method.stop_thread;

/**
 * 暂停线程
 *
 *  可使用suspend()方法暂停线程，使用resume()方法恢复线程执行
 *
 *  缺点：独占，不同步
 *
 * Created by fuzhongyu on 2017/7/1.
 */
public class Suspend {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }


    public static void test1(){

         try {
             SuThr1 thr1=new SuThr1();
             thr1.start();
             thr1.sleep(10);

             thr1.suspend();
             System.out.println("A="+System.currentTimeMillis() + "   i="+thr1.getI());
             thr1.sleep(10);
             System.out.println("B="+System.currentTimeMillis() + "   i="+thr1.getI());
             thr1.resume();
             thr1.sleep(10);
             System.out.println("C="+System.currentTimeMillis() + "   i="+thr1.getI());


         }catch (InterruptedException e){
             e.printStackTrace();
         }
    }


    /**
     * 使用suspend时，易造成公共同步 对象的独占锁导致其他线程无法访问公共资源
     */
    public static void test2(){



            SuSynchronizedObj obj=new SuSynchronizedObj();

            new Thread(){
                @Override
                public void run(){
                    System.out.println("-----thread1------");
                    obj.printString();
                }
            }.start();

            //这边thread2启动了，但永远进不了printString()方法
            new Thread(){
                @Override
                public void run(){
                    System.out.println("-----thread2-----");
                    obj.printString();
                }
            }.start();

    }


    /**
     * 比较test3和test4，test3能输出main end 而test4不能输出main end，原因是System.out.println中含有锁导致独占现象
     *
     * System.out是静态变量，所以所有的System.out.println获取的是同一个变量的锁
     *
     */
    public static void test3(){
        try {
            SuThr1 thr1=new SuThr1();
            thr1.start();
            Thread.sleep(1000);
            thr1.suspend();
            System.out.println("main end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public static void test4(){
        try {
            SuThr2 thr2=new SuThr2();
            thr2.start();
            Thread.sleep(500);
            thr2.suspend();
            System.out.println("main end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }


}


class SuThr1 extends Thread{

    private long i=0;
    public long getI(){
        return i;
    }

    public void setI(){
        this.i=i;
    }

    @Override
    public void run(){
        while (true){
            i++;
        }
    }
}

class SuSynchronizedObj{

    public synchronized void printString(){
        System.out.println("=====begin=====");
        Thread.currentThread().suspend();
        System.out.println("=====end=======");
    }
}


class SuThr2 extends Thread{

    private long i=0;
    public long getI(){
        return i;
    }

    public void setI(){
        this.i=i;
    }

    @Override
    public void run(){
        while (true){
            i++;
            System.out.println(i);
        }
    }
}