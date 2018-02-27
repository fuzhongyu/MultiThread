package com.fzy.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器，执行任务的时间晚于当前时间（在未来执行）,当时间在当前时间之前的时候，任务会立刻执行
 * Created by fuzhongyu on 2017/7/12.
 */
public class TimerMethod{

    public static void main(String[] args) {
        test1();
//        test2();
    }

    /**
     * 定时任务线程启动后会一直运行
     */
    public static void test1(){
        Timer timer=new Timer();

        try {
            TiTask1 afCuTask1=new TiTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse("2017-7-12 17:12:20");
            //在上面的时间点执行任务
            timer.schedule(afCuTask1,date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 主线程结束，定时器就立刻结束
     */
    public static void test2(){
        //将定时任务设置为守护线程
        Timer timer=new Timer(true);

        try {
            TiTask1 afCuTask1=new TiTask1();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse("2017-7-12 17:18:00");
            //在上面的时间点执行任务
            timer.schedule(afCuTask1,date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

class TiTask1 extends TimerTask{

    @Override
    public void run() {
        System.out.println("运行了！时间为："+new Date());
    }
}
