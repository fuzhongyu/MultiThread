package com.fzy.supplement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormat 非线程安全
 *
 * Created by fuzhongyu on 2017/7/17.
 */
public class SimpleDateFormUnsafe {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    /**
     * 多线程下出现错误 ( 用了同一个SimpleDateFormat对象，而这个对象不是线程安全的，所以会出现错误，解决办法：不同线程使用独立的SimpleDateFormat)
     */
    public static void test1(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String[] dataStringArray=new String[]{"2000-01-01","2000-01-02","2000-01-03","2000-01-04","2000-01-05"};
        for(int i=0;i<5;i++){
            new SiDaUnThread1(format,dataStringArray[i]).start();
        }
    }

    /**
     * 解决错误
     */
    public static void test2(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String[] dataStringArray=new String[]{"2000-01-01","2000-01-02","2000-01-03","2000-01-04","2000-01-05"};
        for(int i=0;i<5;i++){
            new SiDaUnThread2(format,dataStringArray[i]).start();
        }
    }

    /**
     * 解决错误
     */
    public static void test3(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String[] dataStringArray=new String[]{"2000-01-01","2000-01-02","2000-01-03","2000-01-04","2000-01-05"};
        for(int i=0;i<5;i++){
            new SiDaUnThread3(format,dataStringArray[i]).start();
        }
    }
}

class SiDaUnThread1 extends Thread{

    private SimpleDateFormat format;
    private String dateString;

    public SiDaUnThread1(SimpleDateFormat format,String dateString){
        this.format=format;
        this.dateString=dateString;
    }

    @Override
    public void run(){
        try {
            Date dateRef=format.parse(dateString);
            String newDateString=format.format(dateRef).toString();
            if(!newDateString.equals(dateString)){
                System.out.println(this.getName()+" 报错了  日期字符串："+dateString+"  转换后日期为："+newDateString);
            }else {
                System.out.println(this.getName()+"  日期字符串："+dateString+"  转换后日期："+newDateString);
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}


class SiDaUnThread2 extends Thread{

    private SimpleDateFormat format;
    private String dateString;

    public SiDaUnThread2(SimpleDateFormat format,String dateString){
        this.format=format;
        this.dateString=dateString;
    }

    @Override
    public void run(){
        try {
            Date dateRef=SiDaUnTools1.parse("yyyy-MM-dd",dateString);
            String newDateString=SiDaUnTools1.format("yyyy-MM-dd",dateRef).toString();
            if(!newDateString.equals(dateString)){
                System.out.println(this.getName()+" 报错了  日期字符串："+dateString+"  转换后日期为："+newDateString);
            }else {
                System.out.println(this.getName()+"  日期字符串："+dateString+"  转换后日期："+newDateString);
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}

class SiDaUnTools1{

    public static Date parse(String formatPattern,String dateString) throws ParseException{
        return new SimpleDateFormat(formatPattern).parse(dateString);
    }

    public static String format(String formatPattern,Date date){
        return new SimpleDateFormat(formatPattern).format(date).toString();
    }
}


class SiDaUnThread3 extends Thread{

    private SimpleDateFormat format;
    private String dateString;

    public SiDaUnThread3(SimpleDateFormat format,String dateString){
        this.format=format;
        this.dateString=dateString;
    }

    @Override
    public void run(){
        try {
            Date dateRef=SiDaUnTools2.getSimpleDateFormat("yyyy-MM-dd").parse(dateString);
            String newDateString=SiDaUnTools2.getSimpleDateFormat("yyyy-MM-dd").format(dateRef).toString();
            if(!newDateString.equals(dateString)){
                System.out.println(this.getName()+" 报错了  日期字符串："+dateString+"  转换后日期为："+newDateString);
            }else {
                System.out.println(this.getName()+"  日期字符串："+dateString+"  转换后日期："+newDateString);
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}

class SiDaUnTools2{
    private static ThreadLocal<SimpleDateFormat> t1=new ThreadLocal<>();

    public static SimpleDateFormat getSimpleDateFormat(String datePattern){
        SimpleDateFormat format=null;
        format=t1.get();
        if(format==null){
            format=new SimpleDateFormat(datePattern);
        }
        return format;
    }
}

