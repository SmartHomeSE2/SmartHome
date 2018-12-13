package com.smarthome.Data;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Request implements Serializable {

    private String type;
    private int deviceId;
    private String value;
    private Date time;


    public Request(String type, int deviceId, String value) {
        this.type = type;
        this.deviceId = deviceId;
        this.value = value;

        time = Calendar.getInstance().getTime();
    }


    public String getType() {
        return type;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getValue() {
        return value;
    }

    public Date getTime() {
        return time;
    }

    @NonNull
    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", deviceId=" + deviceId +
                ", value='" + value + '\'' +
                ", time=" + time +
                '}';
    }
}
