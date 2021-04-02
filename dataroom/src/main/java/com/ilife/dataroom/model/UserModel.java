package com.ilife.dataroom.model;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = UserModel.USER_TABLE_NAME,
        indices = {@Index(value = {UserModel.USER_ID}, unique = true),
                @Index(value = {UserModel.NAME}, unique = true)})
public class UserModel /*implements Parcelable */{

    public static final String USER_TABLE_NAME = "user";

    public static final String USER_ID = "userId";
    public static final String NAME = "name";
    public static final String  GENDER = "gender";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    public long id;

    @NonNull
    @ColumnInfo(name = USER_ID)
    public long userId;

    @NonNull
    @ColumnInfo(name = NAME)
    public String name;

    @NonNull
    @ColumnInfo(name = GENDER)
    public int gender;

    public UserModel(@NonNull long userId, @NonNull String name) {
        this.userId = userId;
        this.name = name;
    }

    @NonNull
    public long getUserId() {
        return userId;
    }

    public void setUserId(@NonNull long userId) {
        this.userId = userId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String toString() {
        return "userId : " + userId + ", name : " + name;
    }
}
