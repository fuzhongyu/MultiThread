package com.fzy.supplement;

/**
 *  线程对象关联线程组：1级关联
 *
 * Created by fuzhongyu on 2017/7/16.
 */
public class OneRelationGroup {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){

        ThreadGroup group=new ThreadGroup("线程组");

        new Thread(group,"a"){
            @Override
            public void run(){
                try {
                    while (true){
                        System.out.println("----"+Thread.currentThread().getName());
                        Thread.sleep(2000);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();


        new Thread(group,"b"){
            @Override
            public void run(){
                try {
                    while (true){
                        System.out.println("----"+Thread.currentThread().getName());
                        Thread.sleep(2100);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("活动的线程数："+group.activeCount());
        System.out.println("线程组名称："+group.getName());


    }
}
