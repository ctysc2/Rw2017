package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/8.
 */

public class FacusListEntity extends BaseEntity {

    public FacusListEntity.DataEntity data;

    public FacusListEntity.DataEntity getData() {
        return data;
    }

    public void setData(FacusListEntity.DataEntity data) {
        this.data = data;
    }

    public static class DataEntity extends BaseApprovementEntity {
        ArrayList<ResLst> resLst;

        public void setResLst(ArrayList<ResLst> resLst) {
            this.resLst = resLst;
        }

        public ArrayList<ResLst> getResLst() {
            return resLst;
        }

        public static class ResLst implements Serializable{
            String userId;
            String realname;
            String avatar;
            String pubNum;
            String supportNum;
            String company;

            public void setCompany(String company) {
                this.company = company;
            }

            public String getCompany() {
                return company;
            }

            public void setSupportNum(String supportNum) {
                this.supportNum = supportNum;
            }

            public String getSupportNum() {
                return supportNum;
            }

            public void setPubNum(String pubNum) {
                this.pubNum = pubNum;
            }

            public String getPubNum() {
                return pubNum;
            }
            public String getAvatar() {
                return avatar;
            }

            public String getRealname() {
                return realname;
            }

            public String getUserId() {
                return userId;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
