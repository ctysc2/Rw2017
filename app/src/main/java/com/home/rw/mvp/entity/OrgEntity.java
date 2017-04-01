package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/26.
 */

public class OrgEntity extends BaseEntity {

    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        private int id;
        private String title;
        private String subTitle;
        private String avatar;
        private String date;
        private String nickname;
        private boolean isExpanded;
        private boolean isAdded;
        private boolean isSelected;
        private  ArrayList<DataEntity> subData;


        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setAdded(boolean added) {
            isAdded = added;
        }

        public boolean isAdded() {
            return isAdded;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setSubData(ArrayList<DataEntity> subData) {
            this.subData = subData;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public ArrayList<DataEntity> getSubData() {
            return subData;
        }

        public boolean isExpanded() {
            return isExpanded;
        }
    }
}
