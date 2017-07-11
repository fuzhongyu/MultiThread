package com.fzy.thread_communication;

/**
 * ThreadLocal  每个线程绑定自己的值
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class ThrLocal {

    public static void main(String[] args) {
//        test1();
        test2();
    }


    public static void test1(){

        new Thread("a"){
            @Override
            public void run(){
                try {
                    ThrLoTools1.local.set("thr1");
                    //休眠2秒，验证ThreadLocal的隔离性，如果不具有隔离性则值会被覆盖
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName()+":"+ThrLoTools1.local.get());
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

        new Thread("b"){
            @Override
            public void run(){
                try {
                    ThrLoTools1.local.set("thr2");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName()+":"+ThrLoTools1.local.get());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 设置ThreadLocal默认值
     */
    public static void test2(){
        System.out.println(ThrLoTools1.local.get());
        System.out.println(ThrLoTools2.localDef.get());
    }
}

class ThrLoTools1{
    public static ThreadLocal local=new ThreadLocal();
}

class ThrLoTools2{
    public static ThreadLocalDef localDef=new ThreadLocalDef();
}

class ThreadLocalDef extends ThreadLocal{
    @Override
    protected Object initialValue(){
        return "设置ThreadLocal默认值";
    }
}
