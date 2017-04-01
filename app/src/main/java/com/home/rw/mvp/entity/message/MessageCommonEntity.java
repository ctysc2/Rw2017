package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;

/**
 * Created by cty on 2017/3/17.
 */

public class MessageCommonEntity implements Serializable{
    private String userId;
    private String realname;
    private String avatar;
    private String phone;
    private String lastSpeakingTime;
    private String nickname;
    private String isFriend;
    private String isFocus;
    private String supportNum;
    private String company;

    public void setLastSpeakingTime(String lastSpeakingTime) {
        this.lastSpeakingTime = lastSpeakingTime;
    }
    public void setSupportNum(String supportNum) {
        this.supportNum = supportNum;
    }
    public String getSupportNum() {
        return supportNum;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public String getLastSpeakingTime() {
        return lastSpeakingTime;
    }

    public void setFocus(String focus) {
        this.isFocus = focus;
    }

    public String getFocus() {
        return isFocus;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getIsFriend() {
            return isFriend;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
