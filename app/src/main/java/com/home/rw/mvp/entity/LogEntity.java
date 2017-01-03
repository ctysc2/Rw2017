package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2016/12/27.
 */

public class LogEntity extends BaseEntity {

    public ArrayList<LogEntity.DataEntity> data;

    public ArrayList<LogEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<LogEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        public String headUrl;
        public String name;
        public String date;
        public String content;

        public void setName(String name) {
            this.name = name;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public String getDate() {
            return date;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
