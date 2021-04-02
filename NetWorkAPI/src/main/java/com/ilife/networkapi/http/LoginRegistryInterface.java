package com.ilife.networkapi.http;

import com.ilife.networkapi.model.BaseResponse;
import com.ilife.networkapi.model.LoginUserInfo;

import org.json.JSONObject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginRegistryInterface {

    @FormUrlEncoded
    @POST("h/sms/sendVerificationCode")
    Observable<BaseResponse<JSONObject>> sendVerificationCode(
            @Field("phone") String phoneNum, @Field("sms_biz_type") String bizType);

    @FormUrlEncoded
    @POST("h/sms/verifyCode")
    Observable<BaseResponse<JSONObject>> checkVerificationCode(
            @Field("phone") String phoneNum, @Field("code") String code, @Field("sms_biz_type") String bizType);

    @FormUrlEncoded
    @POST("h/auth/loginRegistry")
    Observable<BaseResponse<LoginUserInfo>> loginUsePhoneNum(
            @Field("phone") String phoneNum, @Field("captcha") String verifyCode,
            @Field("device_id") String deviceId, @Field("platform") String platform);

    @FormUrlEncoded
    @POST("h/auth/loginRegistry")
    Observable<BaseResponse<LoginUserInfo>> loginUsePhoneNum(
            @Field("phone") String phoneNum, @Field("captcha") String verifyCode,
            @Field("device_id") String deviceId, @Field("platform") String platform,
            @Field("is_debug") boolean is_debug);

    @FormUrlEncoded
    @POST("h/auth/loginByPassword")
    Observable<BaseResponse<LoginUserInfo>> loginUseAccount(
            @Field("phone") String phoneNum, @Field("password") String password,
            @Field("device_id") String deviceId, @Field("platform") String platform);

}
