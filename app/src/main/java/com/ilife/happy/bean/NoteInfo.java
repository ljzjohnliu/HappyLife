package com.ilife.happy.bean;

public class NoteInfo {

    private String code;
    private Integer cost;
    private Integer dailyMaintenanceConsumption;
    private Integer hoursToDowngrade;
    private Integer level;
    private String name;
    private String firstName;
    private String secondName;
    private Integer worth;

    public NoteInfo() {

    }

    public NoteInfo(Integer level, String firstName, String secondName) {
        this.level = level;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getDailyMaintenanceConsumption() {
        return dailyMaintenanceConsumption;
    }

    public void setDailyMaintenanceConsumption(Integer dailyMaintenanceConsumption) {
        this.dailyMaintenanceConsumption = dailyMaintenanceConsumption;
    }

    public Integer getHoursToDowngrade() {
        return hoursToDowngrade;
    }

    public void setHoursToDowngrade(Integer hoursToDowngrade) {
        this.hoursToDowngrade = hoursToDowngrade;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Integer getWorth() {
        return worth;
    }

    public void setWorth(Integer worth) {
        this.worth = worth;
    }

}

