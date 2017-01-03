package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2016/12/29.
 */

public class SignEntity extends BaseEntity {
    public ArrayList<SignEntity.DataEntity> data;

    public ArrayList<SignEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<SignEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity{
        String header;
        String name;
        String time;
        String address;

        public void setAddress(String address) {
            this.address = address;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAddress() {
            return address;
        }

        public String getHeader() {
            return header;
        }

        public String getName() {
            return name;
        }

        public String getTime() {
            return time;
        }
    }
}
