package com.hanami.networkapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ilife.common.model.BaseEntity;

/**
 * {
 *   "code": "string",
 *   "data": {
 *     "area_code": 110000,
 *     "avatar": "http://xxx.com/icon.jpg",
 *     "birthdate": "2000-01-01 00:00:00",
 *     "education": 1,
 *     "height": 180,
 *     "job": 1,
 *     "level": 3,
 *     "nickname": "张三",
 *     "sex": 1,
 *     "signature": "练习生",
 *     "user_id": 1,
 *     "wechat": "we",
 *     "weight": 50
 *   },
 *   "msg": "string",
 *   "trace_id": "string"
 * }
 */

public class UserInfo extends BaseEntity implements Parcelable {

    private int area_code;
    private String avatar;
    private String birthdate;
    private String education;
    private int height;
    private String job;
    private int level;
    private String nickname;
    private int sex;
    private String signature;
    private int user_id;
    private String wechat;
    private int weight;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArea_code() {
        return area_code;
    }

    public void setArea_code(int area_code) {
        this.area_code = area_code;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    protected UserInfo(Parcel in) {
        avatar = in.readString();
        birthdate = in.readString();
        education = in.readString();
        height = in.readInt();
        job = in.readString();
        level = in.readInt();
        nickname = in.readString();
        sex = in.readInt();
        signature = in.readString();
        user_id = in.readInt();
        wechat = in.readString();
        weight = in.readInt();
    }

    public UserInfo(String userId) {
        this.user_id = Integer.valueOf(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeString(birthdate);
        dest.writeString(education);
        dest.writeInt(height);
        dest.writeString(job);
        dest.writeInt(level);
        dest.writeString(nickname);
        dest.writeInt(sex);
        dest.writeString(signature);
        dest.writeInt(user_id);
        dest.writeString(wechat);
        dest.writeInt(weight);
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
