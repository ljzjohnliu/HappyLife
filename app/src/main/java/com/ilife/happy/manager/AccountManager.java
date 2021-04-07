package com.ilife.happy.manager;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.ilife.dataroom.RoomDemoDatabase;
import com.ilife.dataroom.dao.UserDao;
import com.ilife.dataroom.model.UserModel;
import com.ilife.happy.utils.Constants;
import com.tencent.mmkv.MMKV;

import java.util.Date;
import java.util.List;

public class AccountManager {

    private AccountManager() {
    }

    private enum MgrInstance {
        INSTANCE;
        private AccountManager instance;

        MgrInstance() {
            instance = new AccountManager();
        }

        public AccountManager getInstance() {
            return instance;
        }
    }

    public static AccountManager getInstance() {
        return MgrInstance.INSTANCE.getInstance();
    }

    private RoomDemoDatabase roomDemoDatabase;
    private UserDao userDao;

    public void initUserDao(Context context) {
        if (userDao == null) {
            roomDemoDatabase = Room.databaseBuilder(context, RoomDemoDatabase.class, "word_database").allowMainThreadQueries().build();
            userDao = roomDemoDatabase.userDao();
        }
    }

    public boolean registerAccount(String phoneNum, String verifyCode, String password) {
        UserModel userModel = userDao.queryByPhone(phoneNum);
        if (userModel != null) {
            return false;
        } else {
            UserModel user = new UserModel(phoneNum, password, phoneNum);
            long id = userDao.insertOneUser(user);
            MMKV.defaultMMKV().encode(Constants.MMKV_KEY_IS_LOGIN, true);
            MMKV.defaultMMKV().encode(Constants.MMKV_KEY_PHONE, phoneNum);
            MMKV.defaultMMKV().encode(Constants.MMKV_KEY_PASSWORD, password);
            return id > 0;
        }
    }

    public boolean loginAccount(String phoneNum, String password) {
        UserModel userModel = userDao.queryByPhone(phoneNum);
        if (userModel != null) {
            if (userModel.password.equals(password)) {
                MMKV.defaultMMKV().encode(Constants.MMKV_KEY_IS_LOGIN, true);
                MMKV.defaultMMKV().encode(Constants.MMKV_KEY_PHONE, phoneNum);
                MMKV.defaultMMKV().encode(Constants.MMKV_KEY_PASSWORD, password);
                return true;
            }
        }
        return false;
    }

    public void getAccounts() {
        List<UserModel> users = userDao.queryAll();
        Log.d("xxx", "getAccounts: users = " + users + ", size = " + users.size());
        for (UserModel user : users) {
            Log.d("xxx", "getAccounts: user : " + user.toString());
        }
    }

    public static boolean isLogin() {
        boolean isLogin = MMKV.defaultMMKV().decodeBool(Constants.MMKV_KEY_IS_LOGIN);
        Log.d("xxx", "isLogin ： , current isLogin : " + isLogin);
        return isLogin;
    }
}
