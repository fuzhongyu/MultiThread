package com.fzy.supplement;

/**
 * 线程对象关联线程组：多级关联
 *
 * Created by fuzhongyu on 2017/7/16.
 */
public class MultiRelationGroup {

    public static void main(String[] args) {
        test1();
    }


    public static void test1() {

        ThreadGroup mainGroup=Thread.currentThread().getThreadGroup();
        //在mainGroup线程组中添加group线程组
        ThreadGroup group=new ThreadGroup(mainGroup,"group");

        //线程必须启动后才归到线程组A中
        new Thread(group,"a"){
            @Override
            public void run(){
                try {
                    System.out.println("run method");
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //activeGroupCount() 获取当前线程组对象中的子线程组数量
        ThreadGroup[] listGroup=new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
        //复制当前线程组到listGroup
        Thread.currentThread().getThreadGroup().enumerate(listGroup);
        System.out.println("main线程中有多少个子线程组："+listGroup.length+" 名字为："+listGroup[0].getName());

        //线程组中获取线程（存活的线程）
        Thread[] listThread=new Thread[listGroup[0].activeCount()];
        //复制当前线程到listThread
        listGroup[0].enumerate(listThread);
        System.out.println("线程名字："+listThread[0].getName());

    }
}
