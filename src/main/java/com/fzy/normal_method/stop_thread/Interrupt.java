package com.fzy.normal_method.stop_thread;

/**
 * 停止线程:停止线程使用Thread.interrupt() ,也有Thread.stop()方法，但已经废弃不建议使用
 *
 *   (1) this.interrupted(): 测试当前线程是否是已经中断状态，执行后具体有将状态标识清除为false的功能
 *  （2）this.isInterrupted():测试线程Thread对象是否已经是中断状态，但不清除状态标志
 *
 * Created by fuzhongyu on 2017/6/28.
 */
public class Interrupt {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }



    /**
     * interrupt()方法并没有马上停止线程，仅仅是在当前线程中打了一个停止的标记
     */
    public static void test1(){

        try {
            IntThr1 intThr=new IntThr1();
            intThr.start();
            Thread.sleep(2000);

            intThr.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * interrupted()：测试线程是否已经中断，并会清除线程的中断状态
     */
    public static void test2(){

        try {
            IntThr2 intThr=new IntThr2();
            intThr.start();
            Thread.sleep(0);
            intThr.interrupt();
            //中断的是inThr线程，而interrupted()是测试当前线程是否已经中断，当前线程是main所以输出都是false
            System.out.println("是否停止1："+intThr.interrupted());   //false
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("end");
    }


    /**
     * isInterrupted()：测试当前线程是否已经中断，不会清除线程的中断状态
     */
    public static void test3(){
        try {
            IntThr3 intThr=new IntThr3();
            intThr.start();
            Thread.sleep(0);
            intThr.interrupt();
            //中断的是inThr线程，而interrupted()是测试当前线程是否已经中断，当前线程是main所以输出都是false
            System.out.println("是否停止1："+intThr.isInterrupted());   //false
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("end");
    }



}

class IntThr1 extends Thread{
    public void run(){
        for (int i=0;i<5;i++){
            System.out.println("i="+i);
        }
    }
}

class IntThr2 extends Thread{
    public void run(){
        for (int i=0;i<5;i++){
            System.out.println("i="+i);
            System.out.println("InThr线程是否停止："+this.interrupted());
        }
    }
}

class IntThr3 extends Thread{
    public void run(){
        for (int i=0;i<5;i++){
            System.out.println("i="+i);
            System.out.println("InThr线程是否停止："+this.isInterrupted());
        }
    }
}
