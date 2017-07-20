package com.fzy.thread_communication.product_customer;

import java.util.ArrayList;
import java.util.List;

/**
 * 多生产者，多消费者（操作栈）
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class OperationStack {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){

        OprSt1 oprSt1=new OprSt1();

        for (int i=0;i<10;i++){
            new Thread("生产者"+i){
                @Override
                public void run(){
                    while (true){
                        oprSt1.push();
                    }
                }
            }.start();

            new Thread("消费者"+i){
                @Override
                public void run(){
                    while (true){
                        oprSt1.pop();
                    }
                }
            }.start();
        }

    }
}

class OprSt1{

    private List list=new ArrayList();

    synchronized public void push(){
        try {
            while (list.size()==1){
                System.out.println("push中的 "+Thread.currentThread().getName()+" wait了");
                this.wait();
            }
            list.add("anyString="+Math.random());
            //在一生产者，一消费者模式下可以使用notify但在多消费者，多生产者模式下必须使用notifyAll()，以防止假死状态
            this.notifyAll();
            System.out.println("线程 "+Thread.currentThread().getName()+" push="+list.size());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    synchronized public void pop(){
        try {
            if(list.size()==0){
                System.out.println("pop操作中的 "+Thread.currentThread().getName()+" 线程呈wait状态");
                this.wait();
            }
            list.remove(0);
            this.notifyAll();
            System.out.println("线程 "+Thread.currentThread().getName()+" pop="+list.size());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
