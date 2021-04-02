package com.ilife.networkapi.model;

import android.util.Log;


    /*{
        "weatherinfo":{
        "city":"北京",
        "cityid":"101010100",
        "temp1":"18℃",
        "temp2":"31℃",
        "weather":"多云转阴",
        "img1":"n1.gif",
        "img2":"d2.gif",
        "ptime":"18:00"
        }
        }*/

public class WeatherInfoData {
    // 根据返回数据的格式和数据解析方式（Json、XML等）定义
    private WeatherInfo weatherinfo;

    public void show() {
        Log.d("ljz", toString());
    }

    public String toString() {
        if (weatherinfo != null) {
            return "weatherinfo, is city : " + weatherinfo.city
                    + ", cityid : " + weatherinfo.cityid + ", temp1 : " + weatherinfo.temp1
                    + ", temp2 : " + weatherinfo.temp2 + ", weather : " + weatherinfo.weather
                    + ", img1 : " + weatherinfo.img1 + ", img2 : " + weatherinfo.img2 + ", ptime : " + weatherinfo.ptime;
        } else {
            return "weatherinfo is null!!";
        }
    }


    public class WeatherInfo {
        private String city;
        private String cityid;
        private String temp1;
        private String temp2;
        private String weather;
        private String img1;
        private String img2;
        private String ptime;
    }
}