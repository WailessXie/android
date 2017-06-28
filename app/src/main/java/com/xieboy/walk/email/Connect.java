package com.xieboy.walk.email;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Created by Walk on 2017/5/30.
 */

public class Connect {
    private static Store store =null;
    public  static Store login(String host,String user,String password){
        Session session= Session.getDefaultInstance(System.getProperties(),null);
        try{
            store=session.getStore("pop3");
            store.connect(host, user, password);
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
        return store;
    }

    public static String getPOP3Host(String user){
        if (user.contains("163")){
            return "pop.163.com";
        }
        if (user.contains("qq")){
            return "pop.qq.com";
        }
        else return null;//可以添加其他邮箱
    }

    public static String getSMTPHost(String user){
        if (user.contains("163")){
            return "smtp.163.com";
        }
        if (user.contains("qq")){
            return "smtp.qq,com";
        }
        else return null;//可以添加其他邮箱
    }

}
