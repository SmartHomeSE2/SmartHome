package com.smarthome.Data;

public class Device {

    // Type
    public static final int TOGGLE_TYPE = 0;
    public static final int CHECK_TYPE = 1;
    public static final int BURGLAR_ALARM_TYPE = 2;
    private int viewType;

    private int id;
    private String name;
    private String value;
    private String isEnable;

    public Device() {
    }

    public Device(int id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Device(int id, String name, String value, int viewType) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.viewType = viewType;
    }

    public Device(int id, String name, String value, String isEnable, int viewType) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.isEnable = isEnable;
        this.viewType = viewType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public int getViewType() {
        return viewType;
    }
}
