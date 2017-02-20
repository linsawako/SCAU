package com.example.linsawako.scau.bean;

/**
 * Created by linsawako on 2016/12/1.
 */

public class UserInfo {

    private String name;
    private String password;
    private String code;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getPassword() {
        return password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
