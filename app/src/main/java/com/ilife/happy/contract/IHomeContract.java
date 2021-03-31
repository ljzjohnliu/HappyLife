package com.ilife.happy.contract;

import com.ilife.common.model.BaseEntity;
import com.ilife.happy.bean.HomeInfo;

public interface IHomeContract {
    interface Model {
        void executeHomeApi(int type, String msg) throws Exception;
    }

    interface View<T extends BaseEntity> {
        void onResult(HomeInfo t);
    }

    interface Presenter<T extends BaseEntity> {
        void homeApi(int type, String msg);

        void responseResult(T t);
    }
}
