package com.smarthome.Data;

public class Device {

    // Type
    public static final int CONTROL_TYPE = 0;
    public static final int TEMP_IN_TYPE = 1;
    public static final int DISPLAY_TYPE = 2;
    public static final int BURGLAR_ALARM_TYPE = 3;
    private int viewType;

    private int id;
    private String name;
    private String value;
    private String target;

    public Device() {
    }

    public Device(int id,String name,String value){
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Device(int id,String name,String value,int viewType){
        this.id = id;
        this.name = name;
        this.value = value;
        this.viewType = viewType;
    }

    public Device(int id, String name, String value, String target, int viewType) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.target = target;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target_value) {
        this.target = target_value;
    }

    public int getViewType() {
        return viewType;
    }
}
