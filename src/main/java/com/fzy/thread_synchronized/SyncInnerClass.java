package com.fzy.thread_synchronized;

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
        test2();

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
