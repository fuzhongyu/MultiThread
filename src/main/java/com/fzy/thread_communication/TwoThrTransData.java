package com.fzy.thread_communication;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Created by fuzhongyu on 2017/7/7.
 */
public class TwoThrTransData {


    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 同一个变量，利用一个线程监控实现通信（需要一个线程一直监控耗性能，并且未同步）
     */
    public static void test1(){

        TwList twList=new TwList();

        new Thread(){
            @Override
            public void run(){
                try {
                    for (int i=0;i<10;i++){
                        twList.addVal("i="+i);
                        System.out.println("添加了"+(i+1)+"个元素");
                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                try {
                    while (true){
                        Thread.sleep(500);
                        if(twList.getSize()==5){
                            System.out.println("---线程要退出了---");
                            throw new InterruptedException();
                        }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }.start();
    }

    /**
     * 利用等待/通知 机制实现线程交互
     */
    public static void test2(){

        TwList twList=new TwList();

        new Thread("a"){
            @Override
            public void run(){
                try {
                    synchronized (twList){
                        if(twList.getSize()!=5){
                            System.out.println("---wait begin---");
                            twList.wait();
                            System.out.println("---wait end---");
                        }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();

        //保证线程a 先执行，再执行线程b
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("b"){
            @Override
            public void run(){
                synchronized (twList){
                    for (int i=0;i<10;i++){
                        twList.addVal("i="+i);
                        if(twList.getSize()==5){
                            twList.notify();
                            System.out.println("已经发布通知");
                        }
                        System.out.println("添加了元素 i="+i);
                    }

                    //查看notify需要执行完synchronized代码块后才能释放锁，故休眠2秒
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}

class TwList{

    private List<String> list=new ArrayList();

    public void addVal(String val){
        list.add(val);
    }

    public int getSize(){
        return list.size();
    }
}

