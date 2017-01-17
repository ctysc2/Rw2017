package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/1/15.
 */

public class MessegeMainEntity extends BaseEntity{

    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public static class DataEntity{
        String id;
        int type = 0;
        String title;
        String subTitle;
        String date;
        boolean isExpanded;
        public ArrayList<DataEntity> childs;

        public DataEntity(String id,int type,String title,String subTitle,String date,ArrayList<DataEntity> childs,boolean isExpanded){
            this.id = id;
            this.title = title;
            this.type = type;
            this.subTitle = subTitle;
            this.date = date;
            this.childs = childs;
            this.isExpanded = isExpanded;
        }

        public void setChilds(ArrayList<DataEntity> childs) {
            this.childs = childs;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<DataEntity> getChilds() {
            return childs;
        }

        public String getDate() {
            return date;
        }

        public String getId() {
            return id;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public String getTitle() {
            return title;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        public boolean isExpanded() {
            return isExpanded;
        }
    }

}
