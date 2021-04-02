package com.ilife.networkapi.model;

import com.ilife.common.model.BaseEntity;

/**
 * {
 * "code": "A00000",
 * "data": {
 * "authcookie": "string",
 * "device_id": "string",
 * "expire_at": 1610616393757,
 * "ip": "string",
 * "next_page_id": 3,
 * "platform": "string",
 * "user_id": 1
 * },
 * "msg": "string",
 * "trace_id": "string"
 * }
 */

public class LoginUserInfo extends BaseEntity {

    private String authcookie;
    private String device_id;
    private String ip;
    private String platform;
    private long expire_at;
    private int user_id;
    private App_action app_action;

    public String getAuthcookie() {
        return authcookie;
    }

    public void setAuthcookie(String authcookie) {
        this.authcookie = authcookie;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public long getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(long expire_at) {
        this.expire_at = expire_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public App_action getApp_action() {
        return app_action;
    }

    public void setApp_action(App_action app_action) {
        this.app_action = app_action;
    }

    public static class App_action {
        public Op_info op_info;
    }

    public static class Op_info {
        public String action_type;
        public Action_data action_data;
    }

    public static class Action_data {
        public String page_id;
    }
}
