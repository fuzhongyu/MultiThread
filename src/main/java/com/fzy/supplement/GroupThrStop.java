package com.fzy.supplement;

/**
 * 线程组内线程批量停止 ,通过调用group.interrupt()方法将线程组中的线程批量停止
 *
 * Created by fuzhongyu on 2017/7/16.
 */
public class GroupThrStop {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){

        ThreadGroup group=new ThreadGroup("group");

        for (int i=0;i<5;i++){
            new Thread(group,""+i){
                @Override
                public void run(){
                    System.out.println(Thread.currentThread().getName()+" begin ");
                    while (!isInterrupted()){}
                    System.out.println(Thread.currentThread().getName()+"end");
                }
            }.start();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        group.interrupt();
    }
}
