package com.fzy.thread_synchronized;

/**
 * 多线程死锁，当相互等待对方释放锁就有可能出现死锁现象
 *
 * Created by fuzhongyu on 2017/7/6.
 */
public class SyncDeadLock {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        DeaService serv=new DeaService(new Object(),new Object());

        new Thread("a"){
            @Override
            public void run(){
                serv.service();
            }
        }.start();

        new Thread("b"){
            @Override
            public void run(){
                serv.service();
            }
        }.start();
    }

}

/**
 * 锁之间的嵌套调用造成的死锁现象
 */
class DeaService{

    private Object obj1;
    private Object obj2;

    public DeaService(Object obj1,Object obj2){
        this.obj1=obj1;
        this.obj2=obj2;
    }


    public void service(){

        if("a".equals(Thread.currentThread().getName())){
            synchronized (obj1){
                try {
                    System.out.println("----obj1----");
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                synchronized (obj2){
                    System.out.println("obj1-->obj2");
                }
            }


        }else if("b".equals(Thread.currentThread().getName())){
            synchronized (obj2){
                try {
                    System.out.println("---obj2----");
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                synchronized (obj1){
                    System.out.println("obj2-->obj1");
                }
            }


        }
    }
}


