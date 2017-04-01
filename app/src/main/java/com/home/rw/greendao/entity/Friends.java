package com.home.rw.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cty on 2017/3/22.
 */
@Entity
public class Friends {
    @Id
    private Long id;
    private String nickName;
    private String realName;
    private String avatar;
    private String phone;
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getRealName() {
        return this.realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1940445455)
    public Friends(Long id, String nickName, String realName, String avatar,
            String phone) {
        this.id = id;
        this.nickName = nickName;
        this.realName = realName;
        this.avatar = avatar;
        this.phone = phone;
    }
    @Generated(hash = 823074882)
    public Friends() {
    }

}
