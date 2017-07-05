package com.fzy.thread_synchronized;


/**
 * 同步不能被继承
 *
 * Created by fuzhongyu on 2017/7/5.
 */
public class SynchornizedExtend {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 重写父类方法，同步不能被继承
     */
    public static void test1(){

        SyExtServ syExtServ=new SyExtServ();
        new Thread(){

            @Override
            public void run(){
                syExtServ.service("thr1");
            }
        }.start();

        new Thread(){

            @Override
            public void run(){
                syExtServ.service("thr2");
            }
        }.start();

    }

}


class SyExtService{

    public synchronized void service(String name){
        try {
            System.out.println(name+" service start time:"+System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println(name+" service end time:"+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}

class SyExtServ extends SyExtService{

    @Override
    public void service(String name){
        try {
            System.out.println(name+" serv start time:"+System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println(name+" serv end time:"+System.currentTimeMillis());

            super.service(name);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

