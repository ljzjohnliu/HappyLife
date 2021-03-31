package com.ilife.happy.bean;

import com.ilife.common.model.BaseEntity;

public class HomeInfo extends BaseEntity {
    private int type;
    private String msg;

    public HomeInfo(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
