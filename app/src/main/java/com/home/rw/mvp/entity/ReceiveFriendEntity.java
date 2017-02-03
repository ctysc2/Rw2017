package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/2/2.
 */

public class ReceiveFriendEntity extends BaseEntity {

    public ArrayList<ReceiveFriendEntity.DataEntity> data;

    public ArrayList<ReceiveFriendEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<ReceiveFriendEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity{
        private int id;
        private String avatar;
        private String title;
        private String subTitle;
        boolean isApproved;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public void setApproved(boolean approved) {
            isApproved = approved;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public boolean isApproved() {
            return isApproved;
        }
    }
}
