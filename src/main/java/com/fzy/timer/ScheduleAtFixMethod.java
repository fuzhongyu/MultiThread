package com.fzy.timer;

/**
 *  注：(1) schedule()和scheduleAtFixedRate()都会按顺序执行，不需要考虑非线程安全问题
 *     (2) 两者区别主要在于不延时的情况:
 *           schedule()如果执行任务的时间没有被延迟，那么下次执行任务的时间参考的是上一次任务的"开始"时间来计算
 *           scheduleAtFixedRate()如果执行任务的时间没有被延迟，那么下次执行任务的时间餐卡的是上一次任务的"结束"时间来计算
 * Created by fuzhongyu on 2017/7/12.
 */
public class ScheduleAtFixMethod {

    public static void main(String[] args) {

    }

    public static void  test1(){

    }
}


