package com.fzy.thread_communication.ThreadJoin;

/**
 *  join() 后面的代码提前运行出现的意外
 *
 *  这个例子存在争抢thr1锁的情况，如果join先抢到thr1的锁，则main end 会先输出，如下面情况
 *
 *  步骤： (1)thr1.join()先抢到thr1锁，然后将锁释放
 *        (2) 线程a 抢到thr1锁，打印begin，并且sleep(5000)
 *        (3) 线程a打印end ,并释放锁，这个时候thr1.join(2000)已经超过时间了，所以会和线程b一起抢占thr1锁
 *        (4) thr1.join抢占到锁，执行main end ，并释放锁
 *        (5) 线程b抢占到thr1锁，打印begin  sleep后再打印end
 *
 * Created by fuzhongyu on 2017/7/15.
 */
public class JoinAccident {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){

        Thread thr1=new Thread("a"){
            @Override
            synchronized public void run(){
                try {
                    System.out.println(Thread.currentThread().getName()+" begin :"+ System.currentTimeMillis());
                    sleep(1000);
                    System.out.println(Thread.currentThread().getName()+" end :"+System.currentTimeMillis());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thr1.start();

        new Thread("b"){
            @Override
            public void run(){
                synchronized (thr1){
                    try {
                        System.out.println(Thread.currentThread().getName()+" begin :"+ System.currentTimeMillis());
                        sleep(1000);
                        System.out.println(Thread.currentThread().getName()+" end:"+System.currentTimeMillis());
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        try {
            thr1.join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main end:"+System.currentTimeMillis());
    }

}