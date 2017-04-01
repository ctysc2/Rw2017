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
        int id;
        int type = 0;
        String title;
        String nickName;
        String subTitle;
        String avatar;
        String date;
        boolean isExpanded;
        String isFriend;
        String focus;
        String supportNum;
        String company;
        public ArrayList<DataEntity> childs;

        public DataEntity(){

        }

        public DataEntity(int id,int type,String title,String subTitle,String date,ArrayList<DataEntity> childs,boolean isExpanded){
            this.id = id;
            this.title = title;
            this.type = type;
            this.subTitle = subTitle;
            this.date = date;
            this.childs = childs;
            this.isExpanded = isExpanded;
        }

        public void setSupportNum(String supportNum) {
            this.supportNum = supportNum;
        }

        public String getSupportNum() {
            return supportNum;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompany() {
            return company;
        }

        public void setFocus(String focus) {
            this.focus = focus;
        }

        public String getFocus() {
            return focus;
        }
        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
        public String getNickName() {
            return nickName;
        }

        public void setIsFriend(String isFriend) {
            this.isFriend = isFriend;
        }

        public String getIsFriend() {
            return isFriend;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setChilds(ArrayList<DataEntity> childs) {
            this.childs = childs;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setId(int id) {
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

        public int getId() {
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

        public String getAvatar() {
            return avatar;
        }
    }

}
