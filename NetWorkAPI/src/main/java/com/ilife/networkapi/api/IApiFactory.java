package com.ilife.networkapi.api;

/**
 * Api 的工具类 用来返回不同的API对象实体
 */
public abstract class IApiFactory {
  public abstract <T> T makeApiClient(Class<T> cls);

  public abstract <T> T makeWeatherApiClient(Class<T> cls);
}

