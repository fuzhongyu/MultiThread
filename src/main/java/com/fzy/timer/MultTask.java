package com.fzy.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  多个任务及延时
 *
 *  注： 同一个定时器，可以执行多个任务,且是以队列的方式一个一个按顺序执行的，所以执行的时间有可能和预期的时间不一样。如果前面任务执行时间较长，后面任务会被延迟
 *
 * Created by fuzhongyu on 2017/7/12.
 */
public class MultTask {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 是用同一个定时器，task2延迟执行了
     */
    public static void test1(){
        Timer timer=new Timer();
        try {
            MuTask1 task1=new MuTask1();
            MuTask1 task2=new MuTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1=format.parse("2017-7-12 17:36:00");
            Date date2=format.parse("2017-7-12 17:36:01");
            timer.schedule(task1,date1);
            timer.schedule(task2,date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用不同的定时器，task2没有延迟执行
     */
    public static void test2(){
        Timer timer1=new Timer();
        Timer timer2=new Timer();
        try {
            MuTask1 task1=new MuTask1();
            MuTask1 task2=new MuTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1=format.parse("2017-7-12 17:37:00");
            Date date2=format.parse("2017-7-12 17:37:01");
            timer1.schedule(task1,date1);
            timer2.schedule(task2,date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

class MuTask1 extends TimerTask{
    @Override
    public void run(){
        try {
            System.out.println(Thread.currentThread().getName()+" begin "+ new Date());
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName()+" end "+ new Date());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
