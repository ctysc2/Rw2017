package com.home.rw.mvp.entity.base;

/**
 * Created by cty on 16/10/19.
 */
public class BaseEntity {
    public String code;
    public String message;

    public String getMsg() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String status) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }
}
