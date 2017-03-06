package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/2.
 */

public class CardQueryEntity extends BaseEntity{
    private ArrayList<DataEntity> data;

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public ArrayList<DataEntity> getData() {
        return data;
    }
    public static class DataEntity extends BaseApprovementEntity{

        String name;
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
