package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/10.
 */

public class MixFocusEntity extends BaseEntity{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity{

        ArrayList<FacusListEntity.DataEntity.ResLst> focusLst;
        ArrayList<CommunicationEntity.DataEntity.ResLst> topicLst;

        public void setFocusLst(ArrayList<FacusListEntity.DataEntity.ResLst> focusLst) {
            this.focusLst = focusLst;
        }

        public void setTopicLst(ArrayList<CommunicationEntity.DataEntity.ResLst> topicLst) {
            this.topicLst = topicLst;
        }

        public ArrayList<FacusListEntity.DataEntity.ResLst> getFocusLst() {
            return focusLst;
        }

        public ArrayList<CommunicationEntity.DataEntity.ResLst> getTopicLst() {
            return topicLst;
        }

//        public static class FocusLst{
//            String userId;
//            String realname;
//            String avatar;
//            String pubNum;
//
//            public String getUserId() {
//                return userId;
//            }
//
//            public void setUserId(String userId) {
//                this.userId = userId;
//            }
//
//            public String getRealname() {
//                return realname;
//            }
//
//            public void setRealname(String realname) {
//                this.realname = realname;
//            }
//
//            public String getAvatar() {
//                return avatar;
//            }
//
//            public void setAvatar(String avatar) {
//                this.avatar = avatar;
//            }
//
//            public String getPubNum() {
//                return pubNum;
//            }
//
//            public void setPubNum(String pubNum) {
//                this.pubNum = pubNum;
//            }
//        }
//
//        public static class TopicLst{
//            String id;
//            String author;
//            String avatar;
//            String content;
//            String supportNum;
//            String imgs;
//            String createdBy;
//            String createdDate;
//            String focus;
//            String support;
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getAuthor() {
//                return author;
//            }
//
//            public void setAuthor(String author) {
//                this.author = author;
//            }
//
//            public String getAvatar() {
//                return avatar;
//            }
//
//            public void setAvatar(String avatar) {
//                this.avatar = avatar;
//            }
//
//            public String getContent() {
//                return content;
//            }
//
//            public void setContent(String content) {
//                this.content = content;
//            }
//
//            public String getSupportNum() {
//                return supportNum;
//            }
//
//            public void setSupportNum(String supportNum) {
//                this.supportNum = supportNum;
//            }
//
//            public String getImgs() {
//                return imgs;
//            }
//
//            public void setImgs(String imgs) {
//                this.imgs = imgs;
//            }
//
//            public String getCreatedBy() {
//                return createdBy;
//            }
//
//            public void setCreatedBy(String createdBy) {
//                this.createdBy = createdBy;
//            }
//
//            public String getCreatedDate() {
//                return createdDate;
//            }
//
//            public void setCreatedDate(String createdDate) {
//                this.createdDate = createdDate;
//            }
//
//            public String getFocus() {
//                return focus;
//            }
//
//            public void setFocus(String focus) {
//                this.focus = focus;
//            }
//
//            public String getSupport() {
//                return support;
//            }
//
//            public void setSupport(String support) {
//                this.support = support;
//            }
//        }

    }
}
