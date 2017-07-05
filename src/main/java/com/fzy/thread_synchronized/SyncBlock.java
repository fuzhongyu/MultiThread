package com.fzy.thread_synchronized;

/**
 * 同步块
 *
 * 当线程1是用synchronized方法执行一个长时间任务的时候，线程2必须等待较长的时间，这个时候可以使用同步块来提高效率
 *
 * Created by fuzhongyu on 2017/7/5.
 */
public class SyncBlock {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }


    public static void test1(){

        BlService1 blService1=new BlService1();

        new Thread(){

            @Override
            public void run(){
                BlService1.beginTime1=System.currentTimeMillis();
                blService1.service("thr1");
                BlService1.endTime1=System.currentTimeMillis();
            }

        }.start();


        new Thread(){

            @Override
            public void run(){
                BlService1.beginTime2=System.currentTimeMillis();
                blService1.service("thr2");
                BlService1.endTime2=System.currentTimeMillis();
            }

        }.start();

        //等待thr1 和thr2 运行完毕
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long beginT=Math.min(BlService1.beginTime1,BlService1.endTime1);
        long endT=Math.max(BlService1.endTime1,BlService1.endTime2);

        System.out.println("运行同步方法花费的时间："+(endT-beginT)/1000+"秒");

    }


    public static void test2(){

        BlService2 blService2=new BlService2();

        new Thread(){

            @Override
            public void run(){
                BlService2.beginTime1=System.currentTimeMillis();
                blService2.service("thr1");
                BlService2.endTime1=System.currentTimeMillis();
            }

        }.start();


        new Thread(){

            @Override
            public void run(){
                BlService2.beginTime2=System.currentTimeMillis();
                blService2.service("thr2");
                BlService2.endTime2=System.currentTimeMillis();
            }

        }.start();

        //等待thr1 和thr2 运行完毕
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long beginT=Math.min(BlService2.beginTime1,BlService2.endTime1);
        long endT=Math.max(BlService2.endTime1,BlService2.endTime2);

        System.out.println("运行同步方法花费的时间："+(endT-beginT)/1000+"秒");

    }


    public static void test3(){

        BlService3 blService3=new BlService3();

        new Thread(){

            @Override
            public void run(){
                BlService3.beginTime1=System.currentTimeMillis();
                blService3.service("thr1");
                BlService3.endTime1=System.currentTimeMillis();
            }

        }.start();


        new Thread(){

            @Override
            public void run(){
                BlService3.beginTime2=System.currentTimeMillis();
                blService3.service("thr2");
                BlService3.endTime2=System.currentTimeMillis();
            }

        }.start();

        //等待thr1 和thr2 运行完毕
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long beginT=Math.min(BlService3.beginTime1,BlService3.endTime1);
        long endT=Math.max(BlService3.endTime1,BlService3.endTime2);

        System.out.println("运行同步方法花费的时间："+(endT-beginT)/1000+"秒");

    }
}


/**
 * 不进行同步，会出现线程安全问题
 */
class BlService1{

    public static Long beginTime1;
    public static Long endTime1;
    public static Long beginTime2;
    public static Long endTime2;

    private String name;

    public void service(String name){
        try {
            System.out.println(name+" begin time="+System.currentTimeMillis());
            Thread.sleep(1000);
            this.name=name;
            System.out.println("---->name="+this.name);
            System.out.println(name+" end time="+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}

/**
 * 方法同步
 */
class BlService2{

    public static Long beginTime1;
    public static Long endTime1;
    public static Long beginTime2;
    public static Long endTime2;

    private String name;

    //同步方法
    public synchronized void service(String name){
        try {
            System.out.println(name+" begin time="+System.currentTimeMillis());
            Thread.sleep(1000);
            this.name=name;
            System.out.println("---->name="+this.name);
            System.out.println(name+" end time="+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}

/**
 * 对会产生线程安全的代码进行同步块
 */
class BlService3{

    public static Long beginTime1;
    public static Long endTime1;
    public static Long beginTime2;
    public static Long endTime2;

    private String name;

    public void service(String name){
        try {
            System.out.println(name+" begin time="+System.currentTimeMillis());
            Thread.sleep(1000);
            synchronized (this){
                this.name=name;
                System.out.println("---->name="+this.name);
            }
            System.out.println(name+" end time="+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}