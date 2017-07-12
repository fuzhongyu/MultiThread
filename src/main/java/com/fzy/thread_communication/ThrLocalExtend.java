package com.fzy.thread_communication;

/**
 * 使用INheritableThreadLocal可以在子线程中取得父线程的值
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class ThrLocalExtend {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 子线程取得父线程的值
     */
    public static void test1(){
        ThLoExTool1.inheritableThreadLocal.set("main");
        System.out.println("--->main 输出："+ThLoExTool1.inheritableThreadLocal.get());

        new Thread("a"){
            @Override
            public void run(){
                //子线程取父线程的值，如果不能取到则为null，能取到则为main
                System.out.println("==> 子线程输出："+ThLoExTool1.inheritableThreadLocal.get());
            }
        }.start();
    }

    /**
     * 子线程取得父线程的值，并做自己处理
     */
    public static void test2(){
        ThLoExTool2.thLoExCh.set("main");
        System.out.println("--->main 输出："+ThLoExTool2.thLoExCh.get());

        new Thread("a"){
            @Override
            public void run(){
                //子线程取父线程的值，如果不能取到则为null，能取到则为main
                System.out.println("==> 子线程输出："+ThLoExTool2.thLoExCh.get());
            }
        }.start();
    }
}

class ThLoExTool1{
    public static InheritableThreadLocal inheritableThreadLocal=new InheritableThreadLocal();
}


class ThLoExTool2{
    public static ThLoExCh thLoExCh=new ThLoExCh();
}


class ThLoExCh extends InheritableThreadLocal{

    /**
     * 更改初始值
     * @return
     */
    @Override
    protected Object initialValue(){
        return null;
    }

    /**
     * 值继承再修改
     * @param parentValue
     * @return
     */
    @Override
    protected Object childValue(Object parentValue){
        return parentValue+" child thread add";
    }
}





