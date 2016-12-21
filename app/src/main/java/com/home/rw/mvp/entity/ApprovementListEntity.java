package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2016/12/20.
 */

public class ApprovementListEntity extends BaseEntity {

    public ArrayList<DataEntity> data;

    public static class DataEntity {
        public String headUrl;
        public String name;
        public String date;
        public int appStatus;
        private int appType;

        public void setAppType(int appType) {
            this.appType = appType;
        }

        public void setAppStatus(int appStatus) {
            this.appStatus = appStatus;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public void setName(String name) {
            this.name = name;
        }


        public int getAppStatus() {
            return appStatus;
        }

        public String getDate() {
            return date;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public String getName() {
            return name;
        }

        public int getAppType() {
            return appType;
        }
    }
}
