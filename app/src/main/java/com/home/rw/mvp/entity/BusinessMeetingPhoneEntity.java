package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.ui.adapters.BusinessMeetingAdapter;
import com.home.rw.mvp.ui.adapters.CommunicationAdapter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/23.
 */

public class BusinessMeetingPhoneEntity extends BaseEntity {
    public ArrayList<MessegeMainEntity.DataEntity> data;

    public ArrayList<MessegeMainEntity.DataEntity> getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        int id;
        String avatar;
        String title;
        String subTitle;
        String date;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getSubTitle() {
            return subTitle;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public int getId() {
            return id;
        }

        public String getAvatar() {
            return avatar;
        }
    }
}
