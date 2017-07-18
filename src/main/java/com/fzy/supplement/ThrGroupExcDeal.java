package com.fzy.supplement;

/**
 * 线程组内异常处理
 *
 * Created by fuzhongyu on 2017/7/17.
 */
public class ThrGroupExcDeal {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 其中一个线程出现问题，但不影响其他线程的运行
     */
    public static void test1(){

        ThreadGroup group=new ThreadGroup("my group");
        ThrGrExDeThread1[] thrGrExDeThreadList=new ThrGrExDeThread1[5];
        for (int i=0;i<thrGrExDeThreadList.length;i++){
            thrGrExDeThreadList[i]=new ThrGrExDeThread1(group,"线程"+(i+1),"1");
            thrGrExDeThreadList[i].start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThrGrExDeThread1 thr=new ThrGrExDeThread1(group,"报错线程","a");
        thr.start();

    }

    /**
     *    线程组中有一个线程出现错误，都会停止所有的线程
     *
     *  注：通过线程组重写uncaughtException方法处理线程组内线程中断行为时，每个线程对象中的run方法内部不能有异常catch语句，如果有catch语句
     *     则public void uncaughtException(Thread t,Throwable e)方法补执行
     */
    public static void test2(){
        ThrGrExDeThreadGroup group=new ThrGrExDeThreadGroup("my group");
        ThrGrExDeThread2[] thrGrExDeThread2List=new ThrGrExDeThread2[5];
        for (int i=0;i<thrGrExDeThread2List.length;i++){
            thrGrExDeThread2List[i]=new ThrGrExDeThread2(group,"线程"+(i+1),"1");
            thrGrExDeThread2List[i].start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThrGrExDeThread1 thr=new ThrGrExDeThread1(group,"线程报错","a");
        thr.start();
    }



}

class ThrGrExDeThread1 extends Thread{

    private String num;

    public ThrGrExDeThread1(ThreadGroup group,String name,String num){
        super(group,name);
        this.num=num;
    }

    @Override
    public void run(){
        int numInt=Integer.parseInt(num);
        while (true){
            System.out.println("死循环中："+Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class ThrGrExDeThreadGroup extends ThreadGroup{

    public ThrGrExDeThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t,Throwable e){
        super.uncaughtException(t,e);
        this.interrupt();
    }
}


class ThrGrExDeThread2 extends Thread{

    private String num;

    public ThrGrExDeThread2(ThreadGroup group,String name,String num){
        super(group,name);
        this.num=num;
    }

    @Override
    public void run(){
        int numInt=Integer.parseInt(num);
        while (this.isInterrupted()==false){
            System.out.println("死循环中："+Thread.currentThread().getName());
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}




