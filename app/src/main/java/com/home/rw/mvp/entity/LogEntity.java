package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2016/12/27.
 */

public class LogEntity extends BaseEntity {

    public LogEntity.DataEntity data;

    public LogEntity.DataEntity getData() {
        return data;
    }

    public void setData(LogEntity.DataEntity data) {
        this.data = data;
    }

    public static class DataEntity extends BaseApprovementEntity{
        private ArrayList<ResLst> resLst;

        public void setResLst(ArrayList<ResLst> resLst) {
            this.resLst = resLst;
        }

        public ArrayList<ResLst> getResLst() {
            return resLst;
        }

        public static class ResLst{
            String title;
            String author;
            String avatar;
            String content;
            String createdDate;

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getCreatedDate() {
                return createdDate;
            }
            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
