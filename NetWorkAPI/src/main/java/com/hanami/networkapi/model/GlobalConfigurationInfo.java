package com.hanami.networkapi.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.ilife.common.model.BaseEntity;

import java.util.Map;

/**
 *{
 *   "code": "A00000",
 *   "data": {
 *     "system_user_map": {
 *       "brand_assistant": 3,
 *       "apply_enter": 4,
 *       "activity_assistant": 1,
 *       "system_msg": 2
 *     },
 *     "h5_domain": "http://h-node.ilife.cn"
 *   },
 *   "msg": "正常",
 *   "trace_id": "befdf186cfa742799d9db662a7b12d78"
 * }
 */

public class GlobalConfigurationInfo extends BaseEntity {

    private final static String BRAND_ASSISTANT = "brand_assistant";
    private final static String ACTIVITY_ASSISTANT = "activity_assistant";
    private final static String APPLY_ENTER = "apply_enter";
    private final static String SYSTEM_MSG = "system_msg";

    @SerializedName("user_privacy_policy_url")
    String privacyUrl;

    @SerializedName("user_agreement_url")
    String agreementUrl;

    @SerializedName("h5_domain")
    String h5Domain;

    @SerializedName("system_user_map")
    Map<String, String> systemUserMap;

    public String getPrivacyUrl() {
        return privacyUrl;
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public String getH5Domain() {
        return h5Domain;
    }

    public Map<String, String> getSystemUserMap() {
        return systemUserMap;
    }

    public boolean isBrandAssistant(String userId) {
        return !TextUtils.isEmpty(userId) && userId.equalsIgnoreCase(systemUserMap.get(BRAND_ASSISTANT));
    }

    public boolean isActivityAssistant(String userId) {
        return !TextUtils.isEmpty(userId) && userId.equalsIgnoreCase(systemUserMap.get(ACTIVITY_ASSISTANT));
    }

    public boolean isSystemMsg(String userId) {
        return !TextUtils.isEmpty(userId) && userId.equalsIgnoreCase(systemUserMap.get(SYSTEM_MSG));
    }

    public boolean isApplyEnter(String userId) {
        return !TextUtils.isEmpty(userId) && userId.equalsIgnoreCase(systemUserMap.get(APPLY_ENTER));
    }
}
