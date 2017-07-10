package com.fzy.thread_communication.ProductCustomer;

/**
 * 多生产者、多消费者（操作值，假死）
 *
 * Created by fuzhongyu on 2017/7/8.
 */
public class OprValPlayDead {

    public static void main(String[] args) {

    }

    public static void test1(){

    }
}

/**
 * 临界资源
 */
class PlDeObjectVal{
    public static String value="";

}

/**
 * 生产者
 */
class PlDeProduct{

    private String lock;

    public PlDeProduct(String lock) {
        this.lock = lock;
    }

    public void setValue(){
        try {
            synchronized (lock){
                if(!"".equals(PlDeObjectVal.value)){
                    System.out.println("--生产者"+Thread.currentThread().getName()+"wait了");
                    lock.wait();
                }
            }
            System.out.println("-->生产者 "+Thread.currentThread().getName()+" run 了");
            String value=System.currentTimeMillis()+"_"+System.nanoTime();
            PlDeObjectVal.value=value;
            lock.notify();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 消费者
 */
class PlDeCustomer{

    private String lock;

    public PlDeCustomer(String lock) {
        this.lock = lock;
    }

    public void getValue(){
        try {
            synchronized (lock){
                if ("".equals(PlDeObjectVal.value)){
                    System.out.println("==消费者 "+Thread.currentThread().getName()+"wait 了");
                    lock.wait();
                }
                System.out.println("==>消费者 "+Thread.currentThread().getName()+"run 了");
                PlDeObjectVal.value="";
                lock.notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}




