package com.fzy.thread_communication.product_customer;

/**
 * 多生产者、多消费者（操作值，假死）
 *
 * 注：不能将string字段直接作为锁对象，会存在锁对象改变情况。
 *
 * Created by fuzhongyu on 2017/7/8.
 */
public class OperValPlayDead {

    public static void main(String[] args) {
        test1();
//        test2();
    }

    /**
     * 最后会产生所有线程wait的情况（假死）
     */
    public static void test1(){

        for (int i=0;i<2;i++){
            new Thread("生产者"+i){
                @Override
                public void run(){
                    while (true){
                        PlDeProduct1 product=new PlDeProduct1(PlDeObjectVal.value);
                        product.setValue();
                    }
                }
            }.start();

            new Thread("消费者"+i){
                @Override
                public void run(){
                    while (true){
                        PlDeCustomer1 customer=new PlDeCustomer1(PlDeObjectVal.value);
                        customer.getValue();
                    }
                }
            }.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread[] threads=new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        Thread.currentThread().getThreadGroup().enumerate(threads);
        for(int i=0;i<threads.length;i++){
            System.out.println(threads[i].getName()+"--"+threads[i].getState());
        }
    }


    /**
     * 不会产生所有线程wait情况（不会假死）
     */
    public static void test2(){

        for (int i=0;i<2;i++){
            new Thread("生产者"+i){
                @Override
                public void run(){
                    while (true){
                        PlDeProduct2 product=new PlDeProduct2(PlDeObjectVal.value);
                        product.setValue();
                    }
                }
            }.start();

            new Thread("消费者"+i){
                @Override
                public void run(){
                    while (true){
                        PlDeCustomer2 customer=new PlDeCustomer2(PlDeObjectVal.value);
                        customer.getValue();
                    }
                }
            }.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread[] threads=new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        Thread.currentThread().getThreadGroup().enumerate(threads);
        for(int i=0;i<threads.length;i++){
            System.out.println(threads[i].getName()+"--"+threads[i].getState());
        }
    }
}

/**
 * 临界资源
 */
class PlDeObjectVal{
    public static PlDeVal_T value=new PlDeVal_T("");

}

class PlDeVal_T{
    private String str;


    public PlDeVal_T(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}


/**
 * 生产者(唤醒一个线程)
 */
class PlDeProduct1{

    private PlDeVal_T lock;

    public PlDeProduct1(PlDeVal_T lock) {
        this.lock = lock;
    }

    public void setValue(){
        try {
            synchronized (lock){
                while (!"".equals(PlDeObjectVal.value.getStr())){
                    System.out.println("--生产者"+Thread.currentThread().getName()+" wait了");
                    lock.wait();
                }
                System.out.println("-->生产者 "+Thread.currentThread().getName()+" run 了");
                String value=System.currentTimeMillis()+"_"+System.nanoTime();
                PlDeObjectVal.value.setStr(value);
                lock.notify();
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 消费者(唤醒一个线程)
 */
class PlDeCustomer1{

    private PlDeVal_T lock;

    public PlDeCustomer1(PlDeVal_T lock) {
        this.lock = lock;
    }

    public void getValue(){
        try {
            synchronized (lock){
                while ("".equals(PlDeObjectVal.value.getStr())){
                    System.out.println("==消费者 "+Thread.currentThread().getName()+" wait 了");
                    lock.wait();
                }
                System.out.println("==>消费者 "+Thread.currentThread().getName()+" run 了");
                PlDeObjectVal.value.setStr("");
                lock.notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 生产者(唤醒所有线程)
 */
class PlDeProduct2{

    private PlDeVal_T lock;

    public PlDeProduct2(PlDeVal_T lock) {
        this.lock = lock;
    }

    public void setValue(){
        try {
            synchronized (lock){
                while (!"".equals(PlDeObjectVal.value.getStr())){
                    System.out.println("--生产者"+Thread.currentThread().getName()+" wait了");
                    lock.wait();
                }
                System.out.println("-->生产者 "+Thread.currentThread().getName()+" run 了");
                String value=System.currentTimeMillis()+"_"+System.nanoTime();
                PlDeObjectVal.value.setStr(value);
                lock.notifyAll();
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

/**
 * 消费者(唤醒所有线程)
 */
class PlDeCustomer2{

    private PlDeVal_T lock;

    public PlDeCustomer2(PlDeVal_T lock) {
        this.lock = lock;
    }

    public void getValue(){
        try {
            synchronized (lock){
                while ("".equals(PlDeObjectVal.value.getStr())){
                    System.out.println("==消费者 "+Thread.currentThread().getName()+" wait 了");
                    lock.wait();
                }
                System.out.println("==>消费者 "+Thread.currentThread().getName()+" run 了");
                PlDeObjectVal.value.setStr("");
                lock.notifyAll();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}




