package com.fzy.thread_communication;

/**
 * 线程对象wait()
 * Created by fuzhongyu on 2017/7/15.
 */
public class ThreadObjWait {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    /**
     * wait(long) 等待唤醒，或者到达指定时间后自动唤醒
     */
    public static void test1(){

        Object obj=new Object();

        new Thread("a"){
            @Override
            public void run(){
                try {
                    synchronized (obj){
                        System.out.println("--->"+Thread.currentThread().getName());
                        obj.wait(3000);
                        System.out.println(Thread.currentThread().getName()+"等待结束");
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();


        try {
//            Thread.sleep(1000);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("b"){
            @Override
            public void run(){
                synchronized (obj){
                    System.out.println("--->"+Thread.currentThread().getName());
                    obj.notifyAll();
                }
            }
        }.start();

    }


    /**
     * 10秒后输出b wait 结束
     * 此时线程thr1并没有执行thr.start()这种情况和test1()方法是一样的
     */
    public static void test2(){

        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                try {
                    System.out.println("---->"+Thread.currentThread().getName());
                    sleep(2000);
                    System.out.println(Thread.currentThread().getName()+" sleep 结束");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thr2=new Thread("b"){
            @Override
            public void run(){
                try {
                    synchronized (thr1){
                        System.out.println("---->"+Thread.currentThread().getName());
                        thr1.wait(10000);
                        System.out.println(Thread.currentThread().getName()+" wait 结束");
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        thr2.start();
    }

    /**
     * 2秒后输出b wait 结束
     *
     * 此时线程thr1执行thr.start()，当线程执行完毕死亡的时候，该线程对象的wait状态也终止
     */
    public static void test3(){

        Thread thr1=new Thread("a"){
            @Override
            public void run(){
                try {
                    System.out.println("--->"+Thread.currentThread().getName());
                    sleep(2000);
                    System.out.println(Thread.currentThread().getName()+" sleep 结束");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        thr1.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thr2=new Thread("b"){
            @Override
            public void run(){
                try {
                    synchronized (thr1){
                        System.out.println("--->"+Thread.currentThread().getName());
                        thr1.wait(10000);
                        System.out.println(Thread.currentThread().getName()+" wait 结束");
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        thr2.start();
    }
}
