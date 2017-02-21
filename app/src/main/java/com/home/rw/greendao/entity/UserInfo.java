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

    private String userID;
    private String userName;
    private String nickName;
    private String avatar;

    @Generated(hash = 1610275392)
    public UserInfo(Long id, String userID, String userName, String nickName,
            String avatar) {
        this.id = id;
        this.userID = userID;
        this.userName = userName;
        this.nickName = nickName;
        this.avatar = avatar;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public String getAvatar() {
        return avatar;
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
    public String getUserID() {
        return this.userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }


}
