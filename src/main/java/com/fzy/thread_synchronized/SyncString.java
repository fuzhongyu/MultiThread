package com.fzy.thread_synchronized;

/**
 *  string是放在常量池中的，当使用synchronized同步string时，会带来一些列外
 *
 * Created by fuzhongyu on 2017/7/6.
 */
public class SyncString {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 这边只会输出thr1 或者thr2 ,因为string在常量池中，aa属于同一个对象，当其中一个对象持有对象锁的时候，另一个对象将不能再获得对象锁。
     */
    public static void test1(){

        StrService strService=new StrService();

        new Thread("thr1"){
            @Override
            public void run(){
                strService.print("aa");
            }
        }.start();

        new Thread("thr2"){
            @Override
            public void run(){
                strService.print("aa");
            }
        }.start();
    }
}


class StrService{

    public void print(String stringParam){

        try {
            synchronized (stringParam){
                while (true){
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                }

            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
