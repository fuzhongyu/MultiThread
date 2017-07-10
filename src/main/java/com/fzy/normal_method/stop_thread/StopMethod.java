package com.fzy.normal_method.stop_thread;

/**
 * 停止线程--异常法
 *
 * Created by fuzhongyu on 2017/6/30.
 */
public class StopMethod {

    public static void main(String[] args) {
        test1();
    }


    public static void test1(){
        try {
            StoThr1 stoThr1=new StoThr1();
            stoThr1.start();
            stoThr1.sleep(0);
            stoThr1.interrupt();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

}

class StoThr1 extends Thread{

    @Override
    public void run(){

        try {
            for (int i=0;i<5;i++){
                if(this.isInterrupted()){
                    System.out.println("已经是停止状态，退出循环！");
                    //通过抛异常的方式中断当前线程
                    throw new InterruptedException();
                }
                System.out.println("i="+i);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}


