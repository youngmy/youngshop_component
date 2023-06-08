package com.young.cn.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class UserInfo {

    @PrimaryKey
    private int uid;
    private String userName;
    private int age;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserInfo(){

    }

    @Ignore
    public UserInfo(int uid, String userName, int age) {
        this.uid = uid;
        this.userName = userName;
        this.age = age;
    }
}
