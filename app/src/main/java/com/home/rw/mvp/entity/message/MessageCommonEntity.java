package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2017/3/17.
 */

public class MessageCommonEntity{
    private String userId;
    private String realname;
    private String avatar;
    private String phone;
    private String nickname;
    private String isFriend;

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
