package com.ilife.networkapi.model;

import android.text.TextUtils;

public class BaseResponse<T> implements IHJResponse {

  public static String OK_CODE = "A00000";
  public static String SERVER_ERROR_CODE = "E00000";
  public static String WRONG_PARAMETERS_CODE = "E00001";
  public static String AUTHENTICATION_ERROR_CODE = "E00002";
  public static String RATE_LIMITATION_CODE = "E00003";

  private String code;
  private String msg;
  private T data;
  private String trace_id;

  public BaseResponse() {
  }

  public BaseResponse(String code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public void setTrace_id(String trace_id) {
    this.trace_id = trace_id;
  }

  public String getTraceId() {
    return trace_id;
  }

  public boolean isSuccessful() {
    return !TextUtils.isEmpty(code) && "A00000".equals(code);
  }

  public boolean isSuccess() {
    return isSuccessful();
  }

  @Override
  public String toString() {
    return "BaseResponse{" +
        "code='" + code + '\'' +
        ", msg='" + msg + '\'' +
        ", data=" + data +
        '}';
  }

  public static class Builder<T> {
    private String code;
    private String msg;
    private T data;

    public Builder setCode(String code) {
      this.code = code;
      return this;
    }

    public Builder setMsg(String msg) {
      this.msg = msg;
      return this;
    }

    public Builder setData(T data) {
      this.data = data;
      return this;
    }

    public BaseResponse<T> build() {
      return new BaseResponse<>(code, msg, data);
    }

  }

  public static <T> boolean isResponseSuccessful(BaseResponse<T> data) {
    if (data != null && !TextUtils.isEmpty(data.getCode()) && data.getCode().equals("A00000")) {
      return true;
    } else {
      return false;
    }
  }
}
