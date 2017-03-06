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
        String realname;
        String username;
        String sessionId;
        String avatar;
        String rongCloudToken;

        public void setRongCloudToken(String rongCloudToken) {
            this.rongCloudToken = rongCloudToken;
        }

        public String getRongCloudToken() {
            return rongCloudToken;
        }
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setId(String id) {
            this.id = id;
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

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getRealname() {
            return realname;
        }

        public String getSessionId() {
            return sessionId;
        }

        public String getUsername() {
            return username;
        }
    }
}
