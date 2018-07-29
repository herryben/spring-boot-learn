package com.learn.bean;

import java.io.Serializable;

/**
 * Created by xiaohe on 2018/3/20.
 * 因为米粉，所以小米
 */
public class HeaderStatus implements Serializable {
    private int code;
    private String desc;

    public static final HeaderStatus SUCCESS = new HeaderStatus(200, "OK");
    public static final HeaderStatus ERROR = new HeaderStatus(500, "服务端异常");
    public static final HeaderStatus NO_RESULT = new HeaderStatus(404, "没有结果");
    public static final HeaderStatus UNAUTHORIZED = new HeaderStatus(401, "Unauthorized");

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

    public HeaderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public HeaderStatus() {
    }

    public static HeaderStatus builder(int code, String desc) {
        return new HeaderStatus(code, desc);
    }
}
