package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/2/2.
 */

public class MyFriendEntity extends BaseEntity{

    public ArrayList<MyFriendEntity.DataEntity> data;

    public ArrayList<MyFriendEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<MyFriendEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity{
        private int id;
        private String name;
        private String avatar;
        private String date;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getDate() {
            return date;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
