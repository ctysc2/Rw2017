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
    private String avatar;
    private String phone;
    private String gender;
    private String noticeNum;
    private String pubNum;
    public String getPubNum() {
        return this.pubNum;
    }
    public void setPubNum(String pubNum) {
        this.pubNum = pubNum;
    }
    public String getNoticeNum() {
        return this.noticeNum;
    }
    public void setNoticeNum(String noticeNum) {
        this.noticeNum = noticeNum;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
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
    @Generated(hash = 395905980)
    public UserInfo(Long id, String userName, String nickName, String avatar,
            String phone, String gender, String noticeNum, String pubNum) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.avatar = avatar;
        this.phone = phone;
        this.gender = gender;
        this.noticeNum = noticeNum;
        this.pubNum = pubNum;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }


}
