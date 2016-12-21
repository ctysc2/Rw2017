package com.home.rw.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by cty on 2016/12/15.
 */
@Entity
public class UserInfo {
    @Id
    private Long id;

    private String userName;
    private String nickName;
    private String headUrl;
    public String getHeadUrl() {
        return this.headUrl;
    }
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 157107060)
    public UserInfo(Long id, String userName, String nickName, String headUrl) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.headUrl = headUrl;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
}
