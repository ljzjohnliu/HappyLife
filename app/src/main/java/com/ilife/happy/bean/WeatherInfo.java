package com.ilife.happy.bean;

import com.ilife.common.model.BaseEntity;

import java.util.List;

public class WeatherInfo{

    /*参数	描述
    code
    API状态码，具体含义请参考状态码

            updateTime
    当前API的最近更新时间

            fxLink
    当前数据的响应式页面，便于嵌入网站或应用

    now.obsTime
            数据观测时间

    now.temp
    温度，默认单位：摄氏度

    now.feelsLike
    体感温度，默认单位：摄氏度

    now.icon
    天气状况和图标的代码，图标可通过天气状况和图标下载

    now.text
    天气状况的文字描述，包括阴晴雨雪等天气状态的描述

    now.wind360
            风向360角度

    now.windDir
            风向

    now.windScale
            风力等级

    now.windSpeed
    风速，公里/小时

    now.humidity
    相对湿度，百分比数值

    now.precip
    当前小时累计降水量，默认单位：毫米

    now.pressure
    大气压强，默认单位：百帕

    now.vis
    能见度，默认单位：公里

    now.cloud
    云量，百分比数值

    now.dew
            露点温度

    refer.sources
    原始数据来源，或数据源说明，可能为空

    refer.license
    数据许可或版权声明，可能为空*/
    private String code;
    private String updateTime;
    private String fxLink;
    private Now now;
    private Refer refer;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public Refer getRefer() {
        return refer;
    }

    public void setRefer(Refer refer) {
        this.refer = refer;
    }

    public class Now {
        private String obsTime;
        private String temp;
        private String feelsLike;
        private String icon;
        private String text;
        private String wind360;
        private String windDir;
        private String windScale;
        private String windSpeed;
        private String humidity;
        private String precip;
        private String pressure;
        private String vis;
        private String cloud;
        private String dew;

        public String getObsTime() {
            return obsTime;
        }

        public void setObsTime(String obsTime) {
            this.obsTime = obsTime;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(String feelsLike) {
            this.feelsLike = feelsLike;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getWind360() {
            return wind360;
        }

        public void setWind360(String wind360) {
            this.wind360 = wind360;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public String getWindScale() {
            return windScale;
        }

        public void setWindScale(String windScale) {
            this.windScale = windScale;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPrecip() {
            return precip;
        }

        public void setPrecip(String precip) {
            this.precip = precip;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getDew() {
            return dew;
        }

        public void setDew(String dew) {
            this.dew = dew;
        }
    }

   public class Refer {
        private List<String> sources;
        private List<String> license;

        public List<String> getSources() {
            return sources;
        }

        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        public List<String> getLicense() {
            return license;
        }

        public void setLicense(List<String> license) {
            this.license = license;
        }
    }
}
