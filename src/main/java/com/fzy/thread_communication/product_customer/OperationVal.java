package com.fzy.thread_communication.product_customer;

/**
 * 一生产者、一消费者 （操作值）
 *
 * Created by fuzhongyu on 2017/7/8.
 */
public class OperationVal {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){

        OprValueObject lock=new OprValueObject();

        OprProduct1 product1=new OprProduct1(lock);
        OprCustomer1 customer1=new OprCustomer1(lock);

        new Thread("p"){
            @Override
            public void run(){
                while (true){
                    product1.setVal();
                }

            }
        }.start();

        new Thread("c"){
            @Override
            public void run(){
                while (true){
                    customer1.getVal();
                }
            }
        }.start();
    }


}


/**
 * 临界资源
 */
class OprValueObject{
    public static String value="";
}

/**
 * 生产者
 */
class OprProduct1{

    private OprValueObject lock;

    public OprProduct1(OprValueObject lock) {
        this.lock = lock;
    }

    public void setVal(){
        try {
            synchronized (lock){
                while(!"".equals(OprValueObject.value)){
                    System.out.println("生产者wait了");
                    lock.wait();
                }
                String value=System.currentTimeMillis()+"_"+System.nanoTime();
                System.out.println("set 的值是:"+value);
                OprValueObject.value=value;
                lock.notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}

/**
 * 消费者
 */
class OprCustomer1{

    private OprValueObject lock;

    public OprCustomer1(OprValueObject lock) {
        this.lock = lock;
    }

    public void getVal(){
        try {
            synchronized (lock){
                while("".equals(OprValueObject.value)){
                    System.out.println("消费者wait了");
                    lock.wait();
                }
                System.out.println("get 的值是:"+OprValueObject.value);
                OprValueObject.value="";
                lock.notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
