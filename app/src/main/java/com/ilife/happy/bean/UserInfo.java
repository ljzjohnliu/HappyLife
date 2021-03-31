package com.ilife.happy.bean;

import com.ilife.common.model.BaseEntity;

public class UserInfo extends BaseEntity {
    private String nickName;
    private String gender;
    private int age;

    public UserInfo(String nickName, String gender, int age) {
        this.nickName = nickName;
        this.gender = gender;
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

