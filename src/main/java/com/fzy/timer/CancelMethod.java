package com.fzy.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  cancel()将自身任务从任务队列清除
 *
 *  注：（1）timerTask.cancel() 清除的是自身方法，且进程不会被销毁
 *     （2）timer.cancel() 清除的是该定时任务中的全部任务，且进程会被销毁
 *     （3）在执行timer.cancel()的时候，并不一定会立刻停止任务，cancel()需要争抢到queue锁
 *
 *
 * Created by fuzhongyu on 2017/7/12.
 */
public class CancelMethod {

    public static void main(String[] args) {
        test1();
//        test2();
//        test3();
//        test4();
    }

    /**
     * task2 在第一次运行后就停止了，task1还是继续运行
     */
    public static void test1(){

        Timer timer=new Timer();
        try {
            CaMeTask1 task1=new CaMeTask1();
            CaMeTask2 task2=new CaMeTask2();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1=format.parse("2017-7-12 17:36:00");
            Date date2=format.parse("2017-7-12 17:36:01");
            timer.schedule(task1,date1,1000);
            timer.schedule(task2,date2,1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * task2 在运行一次后停止，但进程并没有被销毁
     */
    public static void test4(){
        Timer timer=new Timer();
        CaMeTask2 task2=new CaMeTask2();
        timer.schedule(task2,3000,1000);
    }

    /**
     * task1 和 task3 都被清除,并且进程被销毁
     */
    public static void test2(){

        Timer timer=new Timer();
        try {
            CaMeTask1 task1=new CaMeTask1();
            CaMeTask3 task2=new CaMeTask3();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1=format.parse("2017-7-12 17:37:00");
            Date date2=format.parse("2017-7-12 17:37:01");
            timer.schedule(task1,date1,1000);
            timer.schedule(task2,date2,1000);
            Thread.sleep(500);
            timer.cancel();
        } catch (ParseException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 此处虽然用了timer.cancel()但并没有立刻停止，是因为timer.cancel()需要争抢到queue锁才能停止
     */
    public static void test3(){

        while (true){
            Timer timer=new Timer();
            try {
                CaMeTask1 task1=new CaMeTask1();
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1=format.parse("2017-7-12 17:37:00");
                timer.schedule(task1,date1);
                Thread.sleep(100);
                timer.cancel();
            } catch (ParseException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}

class CaMeTask1 extends TimerTask{

    @Override
    public void run() {
        System.out.println("task1 运行了！当前时间："+new Date());
    }
}

class CaMeTask2 extends TimerTask{

    @Override
    public void run() {
        System.out.println("task2 运行了！当前时间："+new Date());
        //清除该定时任务
       super.cancel();

    }
}

class CaMeTask3 extends TimerTask{

    @Override
    public void run() {
        System.out.println("task3 运行了！当前时间："+new Date());
    }
}



