package com.smarthome;

public class NetResponse {
    private int responseCode;
    private String response;

    public NetResponse() {

    }

    public NetResponse(int responseCode, String response) {
        this.responseCode = responseCode;
        this.response = response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "responseCode: " + getResponseCode() + " " + "response: " + getResponse();
    }
}
