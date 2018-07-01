package com.learn.bean;

public class HttpResponse {
    private int code;
    private String desc;
    public HttpResponse() {
        this.code = 200;
        this.desc = "ok";
    }

    public HttpResponse(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
