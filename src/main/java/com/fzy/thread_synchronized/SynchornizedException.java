package com.fzy.thread_synchronized;

/**
 *  当出现异常的时候，锁会自动释放
 *
 * Created by fuzhongyu on 2017/7/5.
 */
public class SynchornizedException {

    public static void main(String[] args) {
        test1();
    }


    /**
     * 当出现异常的时候，synchronized锁会自动释放
     */
    public static void test1(){

        try {
            ExcService excService=new ExcService();

            SyExThr1 syThr1=new SyExThr1(excService);
            syThr1.setName("a");
            syThr1.start();
            Thread.sleep(100);

            SyExThr2 syExThr2=new SyExThr2(excService);
            syExThr2.setName("b");
            syExThr2.start();

        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }

}

class SyExThr1 extends Thread{

    private ExcService excService;

    public SyExThr1(ExcService excService){
        this.excService=excService;
    }

    @Override
    public void run(){
        excService.service();
    }
}

class SyExThr2 extends Thread{

    private ExcService excService;

    public SyExThr2(ExcService excService){
        this.excService=excService;
    }

    @Override
    public void run(){
        excService.service();
    }
}

class ExcService{

    public synchronized void service(){

        System.out.println("ThreadName="+Thread.currentThread().getName()+"   run beginTime="+System.currentTimeMillis());

        if(Thread.currentThread().getName().equals("a")){
            while (true){
                System.out.println("Exception time="+System.currentTimeMillis());
                //此处异常
                int a=1/0;
            }
        }else if(Thread.currentThread().getName().equals("b")){
            System.out.println("-----execue b----");
        }

        System.out.println("run endTime="+System.currentTimeMillis());
    }
}
