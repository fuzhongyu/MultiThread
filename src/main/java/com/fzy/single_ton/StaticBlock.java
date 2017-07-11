package com.fzy.single_ton;

/**
 * 使用静态块来实现单例(此方法优缺点和饿汉模式类似)
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class StaticBlock {

    private static StaticBlock staticBlock=null;

    private StaticBlock(){}

    static {
        staticBlock=new StaticBlock();
    }

    public static StaticBlock getInstance(){
        return staticBlock;
    }

}
