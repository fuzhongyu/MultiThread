package com.fzy.supplement;

/**
 * 线程组内异常处理
 *
 * Created by fuzhongyu on 2017/7/17.
 */
public class ThrGroupExcDeal {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 其中一个线程出现问题，但不影响其他线程的运行
     */
    public static void test1(){

        ThreadGroup group=new ThreadGroup("my group");
        ThrGrExDeThread[] thrGrExDeThreadList=new ThrGrExDeThread[5];
        for (int i=0;i<5;i++){
            thrGrExDeThreadList[i]=new ThrGrExDeThread(group,"线程"+(i+1),"1");
            thrGrExDeThreadList[i].start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThrGrExDeThread thr=new ThrGrExDeThread(group,"报错线程","a");
        thr.start();

    }
}

class ThrGrExDeThread extends Thread{

    private String num;

    public ThrGrExDeThread(ThreadGroup group,String name,String num){
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
