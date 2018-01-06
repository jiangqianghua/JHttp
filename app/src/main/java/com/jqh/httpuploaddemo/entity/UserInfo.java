package com.jqh.httpuploaddemo.entity;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public class UserInfo {

    private String userName ;

    private String passWord;

    public UserInfo(String userName , String passWord){
        this.userName = userName ;
        this.passWord = passWord ;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getUserName() {
        return userName;
    }
}
