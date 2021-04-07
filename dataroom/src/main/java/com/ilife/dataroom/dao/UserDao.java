package com.ilife.dataroom.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ilife.dataroom.model.UserModel;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT COUNT(*) FROM " + UserModel.USER_TABLE_NAME)
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOneUser(UserModel userModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertUser(UserModel... userModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAllUser(List<UserModel> userModels);

    @Query("SELECT * FROM " + UserModel.USER_TABLE_NAME + " WHERE " +
            UserModel.USER_ID + " = :userId")
    LiveData<UserModel> queryByUserIdLv(long userId);

    @Query("SELECT * FROM " + UserModel.USER_TABLE_NAME + " WHERE " +
            UserModel.USER_ID + " = :userId")
    UserModel queryByUserId(long userId);

    @Query("SELECT * FROM " + UserModel.USER_TABLE_NAME + " WHERE " +
            UserModel.PHONE + " = :phone")
    UserModel queryByPhone(String phone);

    @Query("SELECT * FROM " + UserModel.USER_TABLE_NAME + " WHERE " +
            UserModel.NAME + " = :name")
    UserModel queryByName(String name);

    @Query("SELECT * FROM " + UserModel.USER_TABLE_NAME)
    LiveData<List<UserModel>> queryAllByLv();

    @Query("SELECT * FROM " + UserModel.USER_TABLE_NAME)
    List<UserModel> queryAll();

    @Update
    public int updateUsers(List<UserModel> userModels);

    @Update
    public int updateUser(UserModel... userModels);

    @Query("DELETE FROM " + UserModel.USER_TABLE_NAME + " WHERE " +
            UserModel.USER_ID + " = :userId")
    int deleteByUserId(long userId);

    @Delete
    void delete(UserModel... userModels);

    @Delete
    void deleteAll(List<UserModel> userModels);

    @Query("DELETE FROM " + UserModel.USER_TABLE_NAME)
    void deleteAll();
}