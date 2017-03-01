package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2016/12/10.
 */

public class LoginEntity extends BaseEntity{
    public DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public class DataEntity{
        String id;
        String nickname;
        String username;
        String sessionId;
        String avatar;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getId() {
            return id;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public String getSessionId() {
            return sessionId;
        }

        public String getUsername() {
            return username;
        }
    }
}
