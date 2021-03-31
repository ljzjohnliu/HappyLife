package com.ilife.happy.contract;

import com.ilife.common.model.BaseEntity;
import com.ilife.happy.bean.UserInfo;

public interface IMineContract {
    interface Model {
        void executePersonApi(String name, String pwd) throws Exception;
    }

    interface View<T extends BaseEntity> {
        void onResult(UserInfo t);
    }

    interface Presenter<T extends BaseEntity> {
        void personApi(String name, String pwd);
        void responseResult(T t);
    }
}
