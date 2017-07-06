package com.fzy;

/**
 * 内部类测试
 * Created by fuzhongyu on 2017/7/6.
 */
public class PublicClass {


    static private String username;
    static private String password;

    static class PrivateClass{
        private Integer age;
        private String address;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    class PrivateCls{
        private String sex;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        PublicClass.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        PublicClass.password = password;
    }
}
