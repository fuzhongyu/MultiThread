package com.fzy.single_ton;


import java.io.*;

/**
 * 序列化和反序列化时保证单例模式的方法
 *
 * Created by fuzhongyu on 2017/7/11.
 */
public class SerializeSingleTon{

    public static void main(String[] args) {

        final String url="src/main/java/com/fzy/test_file/myfile.txt";

        //写入磁盘
        try {
            SerSinObject serSinObject=SerSinObject.getInstance();
            FileOutputStream fileOutputStream=new FileOutputStream(url);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(serSinObject);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println(serSinObject.hashCode());
        }catch (IOException e){
            e.printStackTrace();
        }

        //从磁盘读出
        try {
            FileInputStream fileInputStream=new FileInputStream(url);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            SerSinObject serSinObject= (SerSinObject) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            System.out.println(serSinObject.hashCode());
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

class SerSinObject implements Serializable{
    private static final long serialVersionUID=1L;

    private static class ObjectHandler{
        private static final SerSinObject serSinObject=new SerSinObject();
    }

    private SerSinObject(){}

    public static SerSinObject getInstance(){
        return ObjectHandler.serSinObject;
    }

    /**
     * 反序列化的时候，会调用这个方法保证反序列化是同一个对象（如果去掉这个方法，反序列化对象和序列化对象不是同一个，及不是单例）
     * @return
     * @throws ObjectStreamException
     */
    protected Object readResolve() throws ObjectStreamException{
        System.out.println("调用了readResolve方法");
        return ObjectHandler.serSinObject;
    }

}

