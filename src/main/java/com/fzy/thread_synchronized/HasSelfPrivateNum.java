package com.fzy.thread_synchronized;

/**
 *
 * @author fuzhongyu
 * @date 2018/2/24
 */

public class HasSelfPrivateNum {

  public static void main(String[] args) {
    HasSelf hasSelf=new HasSelf();

    new Thread("x"){
      @Override
      public void run(){
        hasSelf.addI("a");
      }
    }.start();

    new Thread("y"){
      @Override
      public void run(){
        hasSelf.addI("b");
      }
    }.start();
  }

}


class HasSelf{
  private int num;

  public void addI(String username){
    try {
      if("a".equals(username)) {
        num = 100;
        System.out.println("a set is over");
        Thread.sleep(2000);
      }else {
        num=200;
        System.out.println("b set is over");
      }
      System.out.println(username+"   num="+num);
    }catch (InterruptedException e){
      e.printStackTrace();
    }
  }
}
