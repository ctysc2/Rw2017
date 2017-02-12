package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/30.
 */

public class MyTeamEntity extends BaseEntity {

    public ArrayList<OrgEntity.DataEntity> data;

    public ArrayList<OrgEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<OrgEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{

        private int id;
        //normal
        private String title;
        private String subTitle;
        private String avatar;
        private String date;

        //add friend
        private boolean isAdded;

        //select
        private boolean isSelected;

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public void setAdded(boolean added) {
            isAdded = added;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public boolean isAdded() {
            return isAdded;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public int getId() {
            return id;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public String getDate() {
            return date;
        }

        public String getTitle() {
            return title;
        }
    }
}
