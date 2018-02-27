package com.fzy.thread_synchronized;

import com.fzy.thread_synchronized.OutClass.InnerClass1;
import com.fzy.thread_synchronized.OutClass.InnerClass2;

/**
 * 内部类与锁
 *
 * 结论：内部类和外部类不是同一个锁，内部类对象和外部类对象也不是同一个锁
 *
 * Created by fuzhongyu on 2017/7/6.
 */
public class SyncInnerClass {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();

    }


    /**
     * 无间隔执行，说明内部类和外部类对象不是同一个锁
     */
    public static void test1(){

        PubClass pubClass=new PubClass();
        PubClass.InnerClass1 innerClass1=pubClass.new InnerClass1();

        new Thread(){
            @Override
            public void run(){
                pubClass.method1();
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                innerClass1.innerMethod1();
            }
        }.start();

    }

    /**
     * 无间隔执行，说明静态内部类的锁和类的锁不是同一个
     */
    public static void test2(){

        new Thread(){
            @Override
            public void run(){
               PubClass.method2();
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                PubClass.InnerClass2.innerMethod3();
            }
        }.start();

    }

    public static void test3(){
        InnerClass1 in1=new InnerClass1();
        InnerClass2 in2=new InnerClass2();
        Thread t1=new Thread("T1"){
            @Override
            public void run(){
                in1.method1(in2);
            }
        };

        Thread t2=new Thread("T2"){
            @Override
            public void run(){
                in1.method2();
            }
        };

        Thread t3=new Thread("T3"){
            @Override
            public void run(){
                in2.method1();
            }
        };
        t1.start();
        t2.start();
        t3.start();
    }

}

class PubClass{

    public synchronized void method1(){
        System.out.println("--- method1 start ---");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---method1 end---");
    }

    public static synchronized void method2(){
        System.out.println("---static method2 start ---");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--- static method2 end---");
    }

    class InnerClass1{

        public synchronized void innerMethod1(){
            System.out.println(" === inner method1 start ====");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("=== inner method1 end ===");
        }

    }

    static class InnerClass2{

        public synchronized void innerMethod2(){
            System.out.println(" === inner method2 start ====");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("=== inner method2 end ===");
        }

        public static synchronized void innerMethod3(){
            System.out.println(" ===static inner method2 start ====");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("===static inner method2 end ===");
        }
    }

}



class OutClass{
    static class InnerClass1{
        public void method1(InnerClass2 class2){
            String threadName= Thread.currentThread().getName();
            synchronized (class2){
                System.out.println(threadName+"进入InnerClass1类中的method1方法中");
                for (int i=0;i<10;i++){
                    System.out.println("i="+i);
//                    try {
//                        Thread.sleep(100);
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
                }
                System.out.println(threadName+"离开InnerClass1类中的method1方法中");
            }

        }

        public synchronized void method2(){
            String threadName= Thread.currentThread().getName();
            System.out.println(threadName+"进入InnerClass1类中的method2方法中");
            for (int j=0;j<10;j++){
                System.out.println("j="+j);
//                try {
//                    Thread.sleep(100);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
            }
            System.out.println(threadName+"离开InnerClas1类中的method2方法中");
        }
    }

    static class InnerClass2{
        public synchronized void method1(){
            String threadName= Thread.currentThread().getName();
            System.out.println(threadName+"进入InnerClass2类中的method1方法中");
            for (int k=0;k<10;k++){
                System.out.println("k="+k);
//                try {
//                    Thread.sleep(100);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
            }
            System.out.println(threadName+"离开InnerClass2类中的method1方法中");
        }
    }
}
