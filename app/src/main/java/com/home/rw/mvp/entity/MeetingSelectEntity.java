package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/2/4.
 */

public class MeetingSelectEntity extends BaseEntity{
    public ArrayList<MessegeMainEntity.DataEntity> data;

    public ArrayList<MessegeMainEntity.DataEntity> getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        String userId;
        int id;
        String title;
        String avatar;
        String nickname;
        String phone;
        boolean isSelected;
        boolean isExpanded;
        ArrayList<DataEntity> subData;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setSubData(ArrayList<DataEntity> subData) {
            this.subData = subData;
        }

        public int getId() {
            return id;
        }

        public String getAvatar() {
            return avatar;
        }

        public ArrayList<DataEntity> getSubData() {
            return subData;
        }

        public String getTitle() {
            return title;
        }

        public boolean isSelected() {
            return isSelected;
        }
    }
}
