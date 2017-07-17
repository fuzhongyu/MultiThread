package com.fzy.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * (1) isFair() 判断是不是公平锁
 *
 * (2) isHeldByCurrentThread() 查询当前是否保持此锁
 *
 * (3) isLocked() 查询此锁定是否由任意线程保持
 *
 * Created by fuzhongyu on 2017/7/15.
 */
public class IsMethod {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        IsMeService1 isMeService1=new IsMeService1(true);
        isMeService1.service1();
        isMeService1.service2();
        isMeService1.service3();

    }
}

class IsMeService1{

    private ReentrantLock lock;

    public IsMeService1(boolean isFair){
        lock=new ReentrantLock(isFair);
    }

    public void service1(){
        try {
            lock.lock();
            if(lock.isFair()){
                System.out.println("是公平锁");
            }else {
                System.out.println("不是公平锁");
            }
        }finally {
            lock.unlock();
        }
    }

    public void service2(){
        try {
            System.out.println("当前线程是否保持此锁定1="+lock.isHeldByCurrentThread());
            lock.lock();
            System.out.println("当前线程是否保持此锁定2="+lock.isHeldByCurrentThread());
        }finally {
            lock.unlock();
        }
    }

    public void service3(){
        try {
            System.out.println("锁定是由该线程保持1="+lock.isLocked());
            lock.lock();
            System.out.println("锁定是由该线程保持2="+lock.isLocked());
        }finally {
            lock.unlock();
        }
    }

}
