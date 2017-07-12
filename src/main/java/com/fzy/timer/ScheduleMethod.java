package com.fzy.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * schedule方法测试
 *
 * Created by fuzhongyu on 2017/7/12.
 */
public class ScheduleMethod {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
        test5();
    }

    /**
     * 如果设点的时间早于当前时间则立刻执行定时任务
     */
    public static  void  test1(){
        Timer timer=new Timer();
        try {
            ScMeTask1 afCuTask1=new ScMeTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse("2017-7-12 17:50:20");
            timer.schedule(afCuTask1,date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    /**
     * 如果设点的时间迟于当前时间则到达时间点后执行定时任务
     */
    public static  void  test2(){
        Timer timer=new Timer();
        try {
            ScMeTask1 afCuTask1=new ScMeTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse("2017-7-12 17:53:20");
            timer.schedule(afCuTask1,date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 开始定时任务后，每3秒执行一次，永不停止
     */
    public static  void  test3(){
        Timer timer=new Timer();
        try {
            ScMeTask1 afCuTask1=new ScMeTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse("2017-7-12 17:55:20");
            //在上面的时间点后执行任务，并在每3秒执行一次，永不停止
            timer.schedule(afCuTask1,date,3000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 在当前时间的基础上，延迟3秒执行任务
     */
    public static  void  test4(){
        Timer timer=new Timer();
        try {
            ScMeTask1 afCuTask1=new ScMeTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse("2017-7-12 17:55:20");
            //以当前时间为标准，延迟3秒执行
            timer.schedule(afCuTask1,3000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 在当前时间的基础上，延迟3秒执行任务,之后每隔1秒执行该任务
     */
    public static  void  test5(){
        Timer timer=new Timer();
        try {
            ScMeTask1 afCuTask1=new ScMeTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse("2017-7-12 17:55:20");
            //以当前时间为标准，延迟3秒执行后，每隔1秒执行该任务
            timer.schedule(afCuTask1,3000,1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}

class ScMeTask1 extends TimerTask{
    @Override
    public void run() {
        System.out.println("运行了！时间为："+new Date());
    }
}
