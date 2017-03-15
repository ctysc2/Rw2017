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
    private String realName;
    private String avatar;
    private String phone;
    private String gender;
    private String noticeNum;
    private String pubNum;
    private String company;
    private String focusNum;
    public String getFocusNum() {
        return this.focusNum;
    }
    public void setFocusNum(String focusNum) {
        this.focusNum = focusNum;
    }
    public String getCompany() {
        return this.company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
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
    @Generated(hash = 821443322)
    public UserInfo(Long id, String userName, String nickName, String realName,
            String avatar, String phone, String gender, String noticeNum,
            String pubNum, String company, String focusNum) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.realName = realName;
        this.avatar = avatar;
        this.phone = phone;
        this.gender = gender;
        this.noticeNum = noticeNum;
        this.pubNum = pubNum;
        this.company = company;
        this.focusNum = focusNum;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }






}
