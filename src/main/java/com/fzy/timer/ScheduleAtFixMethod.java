package com.fzy.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  注：(1) schedule()和scheduleAtFixedRate()都会按顺序执行，不需要考虑非线程安全问题
 *     (2) 两者区别主要在于不延时的情况（延时的时候没区别）:
 *           schedule()如果执行任务的时间没有被延迟，那么下次执行任务的时间参考的是上一次任务的"开始"时间来计算
 *           scheduleAtFixedRate()如果执行任务的时间没有被延迟，那么下次执行任务的时间的是上一次任务的"结束"时间来计算
 * Created by fuzhongyu on 2017/7/12.
 */
public class ScheduleAtFixMethod {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    /**
     * schedule() 如果没有延时，则下一次执行任务的时间是上一次任务的开始时间加上定时器执行间隔时间
     */
    public static void  test1(){
        Timer timer=new Timer();
        try {
            ScAtTask1 task1=new ScAtTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1=format.parse("2017-7-12 17:36:00");
            timer.schedule(task1,date1,3000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * schedule() 如果延时，则下一次执行任务的时间是上一次任务结束时间
     */
    public static void  test2(){
        Timer timer=new Timer();
        try {
            ScAtTask1 task1=new ScAtTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1=format.parse("2017-7-12 17:39:00");
            timer.schedule(task1,date1,1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * scheduleAtFixedRate() 如果没有延时，则下一次执行任务的时间是上一次任务的开始时间加上定时器执行间隔时间
     */
    public static void  test3(){
        Timer timer=new Timer();
        try {
            ScAtTask1 task1=new ScAtTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1=format.parse("2017-7-12 17:36:00");
            timer.scheduleAtFixedRate(task1,date1,3000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * scheduleAtFixedRate() 如果没有延时，则下一次执行任务的时间是上一次任务的开始时间加上定时器执行间隔时间
     */
    public static void  test4(){
        Timer timer=new Timer();
        try {
            ScAtTask1 task1=new ScAtTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1=format.parse("2017-7-12 17:36:00");
            timer.scheduleAtFixedRate(task1,date1,1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}

class ScAtTask1 extends TimerTask{
    @Override
    public void run(){
        try {
            System.out.println("begin="+new Date());
            Thread.sleep(2000);
            System.out.println("end="+new Date());
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}


