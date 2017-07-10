package com.fzy.thread_synchronized;

/**
 *  volatile关键字，使变量在多个线程间可见
 *
 * Created by fuzhongyu on 2017/7/6.
 */
public class VolatileKeyWord {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * main线程一直在执行while 循环，不能执行后续代码
     */
    public static void test1(){
        VolService1 volService=new VolService1();
        volService.service();
        System.out.println("===停止===");
        volService.setFlag(false);
    }

    /**
     * 利用多线程技术可以实现停止
     *
     * 注：在server服务器中，还是会出现死循环。 在server模式时，变量private Boolean flag=true;存储在公共堆栈中
     *    而为了线程运行的效率，线程一直在私有堆栈中 取得flag的值，虽然 volService.setFlag(false);代码执行，但
     *    改变的是公共堆栈中的变量，私有堆栈中的flag一直是true
     *
     */
    public static void test2(){
        VolService2 volService=new VolService2();
        volService.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===停止===");
        volService.setFlag(false);
    }

    /**
     * 利用volatile关键字定义变量，可以在私有内存和共有内存同步数据
     */
    public static void test3(){
        VolService3 volService=new VolService3();
        volService.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===停止===");
        volService.setFlag(false);
    }


}

class VolService1{

    private Boolean flag=true;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void service(){
        try {
            while (flag){
                System.out.println("run service thread name="+Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class VolService2 extends Thread {

    private Boolean flag=true;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void service(){
        try {
            while (flag){
                System.out.println("run service thread name="+Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        service();
    }
}

/**
 * 是用volatile指定变量，使变量flag强制从公共堆栈中取值
 */
class VolService3 extends Thread {

   volatile private Boolean flag=true;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void service(){
        try {
            while (flag){
                System.out.println("run service thread name="+Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        service();
    }
}

