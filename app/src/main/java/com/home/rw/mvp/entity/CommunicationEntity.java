package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/6.
 */

public class CommunicationEntity extends BaseEntity {
    public CommunicationEntity.DataEntity data;

    public CommunicationEntity.DataEntity getData() {
        return data;
    }

    public void setData(CommunicationEntity.DataEntity data) {
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

        public static class ResLst implements Serializable{
            String id;
            String title;
            String author;
            String avatar;
            String content;
            String supportNum;
            String imgs;
            String createdBy;
            String createdDate;
            String focus;
            String support;

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle() {
                return title;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getSupportNum() {
                return supportNum;
            }

            public void setSupportNum(String supportNum) {
                this.supportNum = supportNum;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getFocus() {
                return focus;
            }

            public void setFocus(String focus) {
                this.focus = focus;
            }

            public String getSupport() {
                return support;
            }

            public void setSupport(String support) {
                this.support = support;
            }
        }


    }
}
