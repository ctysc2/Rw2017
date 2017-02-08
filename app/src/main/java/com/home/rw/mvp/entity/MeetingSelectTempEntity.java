package com.home.rw.mvp.entity;

import java.io.Serializable;

/**
 * Created by cty on 2017/2/4.
 */

public class MeetingSelectTempEntity implements Serializable{
    private int id;
    private String avatar;
    private String name;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
