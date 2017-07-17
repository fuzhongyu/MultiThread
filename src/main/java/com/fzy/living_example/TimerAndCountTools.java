package com.fzy.living_example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  启动计时和计数，如果在MAX_TIME分钟内borderResList达到MAX_COUNT，应立即执行方法，计时和计数重新开始；
 *  如果在MAX_TIME分钟内borderResList数据不足MAX_COUNT，也执行方法，计时和计数重新开始；
 *
 * Created by fuzhongyu on 2017/7/13.
 */
public class TimerAndCountTools {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 没有达到规定时间list达到最大值10执行方法
     */
    public static void test1(){
        try {
            for (int i=0;i<15;i++){
                TiCo.addData("i="+i);
                Thread.sleep(300);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * 在最大时间list没达到最大值， 执行方法
     */
    public static void test2(){
        for (int i=0;i<5;i++){
            TiCo.addData("i="+i);
        }
    }


}

class TiCo{
    //时间
    private final static long MAX_TIME=10000;
    //最大条数
    private final static int MAX_COUNT=10;
    //需要控制的临界资源
    public static List<String> borderResList=new ArrayList<>();


    static {
        new Thread("regulator"){
            @Override
            public void run(){
                try {
                    synchronized (borderResList){
                        while (true){
                            long beginTime=System.currentTimeMillis();

                            System.out.println("开始计时,时间："+new Date());
                            borderResList.wait(MAX_TIME);

                            execMethod(borderResList);
                            //清除borderResList中的资源
                            borderResList.clear();

                            long endTime=System.currentTimeMillis();
                            System.out.println("用时："+(endTime-beginTime)/1000+" 秒");
                        }

                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 添加数据
     * @param str
     */
    public static void addData(String str){
        synchronized (borderResList){
            borderResList.add(str);
            if(borderResList.size()==MAX_COUNT){
                borderResList.notifyAll();
            }
        }
    }

    /**
     * 满足条件时执行方法
     * @param list
     */
    public static void execMethod(List<String> list){
        System.out.println("执行方法——execMethod,时间："+new Date()+"   list长度:"+list.size());
    }

}



