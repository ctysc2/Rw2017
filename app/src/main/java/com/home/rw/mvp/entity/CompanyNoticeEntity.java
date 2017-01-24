package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/18.
 */

public class CompanyNoticeEntity extends BaseEntity{
    public ArrayList<CompanyNoticeEntity.DataEntity> data;

    public ArrayList<CompanyNoticeEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<CompanyNoticeEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable {
        String title;
        String date;
        String content;

        public void setContent(String content) {
            this.content = content;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public String getDate() {
            return date;
        }

        public String getTitle() {
            return title;
        }
    }
}
