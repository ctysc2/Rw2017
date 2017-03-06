package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2016/12/28.
 */

public class RollMeEntity extends BaseEntity {

    public RollMeEntity.DataEntity data;

    public RollMeEntity.DataEntity getData() {
        return data;
    }

    public void setData(RollMeEntity.DataEntity data) {
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
            String author;
            String avatar;
            String beginTime;
            String endTime;
            String content;
            boolean isExpand = false;

            public boolean isExpand() {
                return isExpand;
            }

            public void setExpand(boolean expand) {
                isExpand = expand;
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

            public String getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
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
