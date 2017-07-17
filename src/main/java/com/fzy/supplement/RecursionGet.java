package com.fzy.supplement;

/**
 * 递归与非递归取得组内对象
 *
 * Created by fuzhongyu on 2017/7/16.
 */
public class RecursionGet {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){

        ThreadGroup mainGroup=Thread.currentThread().getThreadGroup();
        ThreadGroup groupA=new ThreadGroup(mainGroup,"A");
        ThreadGroup groupB=new ThreadGroup(groupA,"B");

        ThreadGroup[] listGroup1=new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
        //传入true是递归取得子组及子孙组 (默认为递归)
        Thread.currentThread().getThreadGroup().enumerate(listGroup1,true);
        for (ThreadGroup group:listGroup1){
            if(group!=null){
                System.out.println("listGroup1:"+group.getName());
            }

        }

        ThreadGroup[] listGroup2=new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
        Thread.currentThread().getThreadGroup().enumerate(listGroup2,false);
        for (ThreadGroup group:listGroup2){
            if(group!=null){
                System.out.println("listGroup2:"+group.getName());
            }
        }

    }

}
