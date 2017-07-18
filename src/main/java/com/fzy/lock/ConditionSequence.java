package com.fzy.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition实现顺序执行
 *
 * Created by fuzhongyu on 2017/7/18.
 */
public class ConditionSequence {


    public static void main(String[] args) {
        test1();
    }


    volatile private static int nextPrintWho=1;
    private static ReentrantLock lock=new ReentrantLock();
    private static Condition condition_A=lock.newCondition();
    private static Condition condition_B=lock.newCondition();
    private static Condition condition_C=lock.newCondition();

    public static void test1(){

        Thread thrA=new Thread("a"){
            @Override
            public void run(){
                while (true){
                    try {
                        lock.lock();
                        while (nextPrintWho!=1){
                            condition_A.await();
                        }
                        for (int i=0;i<3;i++){
                            System.out.println("Thread_A:"+(i+1));
                        }
                        nextPrintWho=2;
                        condition_B.signalAll();
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        };

        Thread thrB=new Thread("b"){
            @Override
            public void run(){
                while (true){
                    try {
                        lock.lock();
                        while (nextPrintWho!=2){
                            condition_B.await();
                        }
                        for (int i=0;i<3;i++){
                            System.out.println("Thread_B:"+(i+1));
                        }
                        nextPrintWho=3;
                        condition_C.signalAll();
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }

            }
        };

        Thread thrC=new Thread("c"){
            @Override
            public void run(){
                while (true){
                    try {
                        lock.lock();
                        while (nextPrintWho!=3){
                            condition_C.await();
                        }
                        for (int i=0;i<3;i++){
                            System.out.println("Thread_C:"+(i+1));
                        }
                        nextPrintWho=1;
                        condition_A.signalAll();
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        };

        thrA.start();
        thrB.start();
        thrC.start();


    }
}


