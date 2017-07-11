package com.fzy.thread_communication;

import java.io.*;

/**
 * 线程间的通信（字节流,字符流）
 *
 * 管道流（pipeStream），用于在不同线程间直接传输数据 (PipedInputStream,PipedOutputStream,PipedReader,PipedWriter)
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class ThreadIo {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 管道字节流
     */
    public static  void test1(){
        PipedOutputStream out=new PipedOutputStream();
        PipedInputStream in=new PipedInputStream();

        try {
            in.connect(out);

            new Thread("a"){
                @Override
                public void run(){
                    new ThrOutputStream().outputMethod(out);
                }
            }.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread("b"){
                @Override
                public void run(){
                    new ThrInputStream().inputMethod(in);
                }
            }.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 管道字符流
     */
    public static void test2(){
        PipedWriter writer=new PipedWriter();
        PipedReader reader=new PipedReader();

        try {
            writer.connect(reader);

            new Thread("a"){
                @Override
                public void run(){
                    new ThrWrite().writeMethod(writer);
                }
            }.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread("b"){
                @Override
                public void run(){
                    new ThrRead().readMethod(reader);
                }
            }.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

/**
 * 管道输出字节流
 */
class ThrOutputStream{

    public void outputMethod(PipedOutputStream out){
        try {
            System.out.println("write :");
            for (int i=0;i<10;i++){
                String data=" "+(i+1);
                out.write(data.getBytes());
                System.out.print(data+" ");
            }
            System.out.println();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

class ThrInputStream{

    public void inputMethod(PipedInputStream in){

        try {
            System.out.println(" read :");
            byte[] bytes=new byte[100];
            int len=0;
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            while ((len=in.read(bytes))!=-1){
               byteArrayOutputStream.write(bytes);
            }
            System.out.println(byteArrayOutputStream.toString());
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

/**
 * 管道输出字符流
 */
class ThrWrite{

    public void writeMethod(PipedWriter writer){
        try {
            System.out.println("write :");
            for (int i=0;i<10;i++){
                String data=" "+(i+1);
                writer.write(data);
                System.out.print(data+" ");
            }
            System.out.println();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

class ThrRead{

    public void readMethod(PipedReader reader){

        try {
            System.out.println(" read :");
            char[] chars=new char[100];
            int len=0;
            CharArrayWriter charArrayWriter=new CharArrayWriter();
            while ((len=reader.read(chars))!=-1){
                charArrayWriter.write(chars);
            }
            System.out.println(charArrayWriter.toString());
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}




