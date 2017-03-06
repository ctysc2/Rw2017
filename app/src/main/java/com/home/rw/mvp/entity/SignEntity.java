package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2016/12/29.
 */

public class SignEntity extends BaseEntity {
    private SignEntity.DataEntity data;

    public void setData(SignEntity.DataEntity data) {
        this.data = data;
    }

    public SignEntity.DataEntity getData() {
        return data;
    }

    public static class DataEntity extends BaseApprovementEntity {
        ArrayList<SignEntity.DataEntity.ResLst> resLst;


        public void setResLst(ArrayList<SignEntity.DataEntity.ResLst> resLst) {
            this.resLst = resLst;
        }

        public ArrayList<SignEntity.DataEntity.ResLst> getResLst() {
            return resLst;
        }

        public static class ResLst{
            String name;
            String realname;
            String avatar;
            String signInTime;
            String longitude;
            String latitude;
            int type;
            String remark;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getSignInTime() {
                return signInTime;
            }

            public void setSignInTime(String signInTime) {
                this.signInTime = signInTime;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }

    }
}
