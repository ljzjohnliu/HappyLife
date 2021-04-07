package com.ilife.dataroom.model;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = UserModel.USER_TABLE_NAME,
        indices = {@Index(value = {UserModel.USER_ID}, unique = true),
                @Index(value = {UserModel.PHONE}, unique = true),
                @Index(value = {UserModel.PASSWORD}, unique = false),
                @Index(value = {UserModel.NAME}, unique = true),
                @Index(value = {UserModel.AVATAR}, unique = false),
                @Index(value = {UserModel.GENDER}, unique = false),
                @Index(value = {UserModel.AGE}, unique = false),
                @Index(value = {UserModel.BIRTHDAY}, unique = false),
                @Index(value = {UserModel.LEVEL}, unique = false),
                @Index(value = {UserModel.HEIGHT}, unique = false),
                @Index(value = {UserModel.WEIGHT}, unique = false)
        })
public class UserModel /*implements Parcelable */ {

    public static final String USER_TABLE_NAME = "user";

    public static final String USER_ID = "userId";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String AVATAR = "avatar";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String BIRTHDAY = "birthday";
    public static final String LEVEL = "level";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";

//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = BaseColumns._ID)
//    public long id;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = USER_ID)
    public long userId;

    @NonNull
    @ColumnInfo(name = PHONE)
    public String phone;

    @NonNull
    @ColumnInfo(name = PASSWORD)
    public String password;

    @NonNull
    @ColumnInfo(name = NAME)
    public String name;

    @ColumnInfo(name = AVATAR)
    public String avatar;

    @ColumnInfo(name = GENDER)
    public int gender;

    @ColumnInfo(name = AGE)
    public int age;

    @ColumnInfo(name = BIRTHDAY)
    public String birthday;

    @ColumnInfo(name = LEVEL)
    public int level;

    @ColumnInfo(name = HEIGHT)
    public float height;

    @ColumnInfo(name = WEIGHT)
    public float weight;

    @Ignore
    public UserModel(@NonNull String phone, @NonNull String password, @NonNull String name) {
        this(phone, password, name, null, 0, 0, null, 0, 0f, 0f);
    }

    public UserModel(@NonNull String phone, @NonNull String password, @NonNull String name,
                     String avatar, int gender, int age, String birthday, int level, float height, float weight) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.level = level;
        this.height = height;
        this.weight = weight;
    }

    public String toString() {
        return "userId : " + userId + ", name : " + name;
    }
}
