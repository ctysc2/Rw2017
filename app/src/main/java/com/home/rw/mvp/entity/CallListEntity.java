package com.home.rw.mvp.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/24.
 */

public class CallListEntity {
    public ArrayList<CallListEntity.DataEntity> data;

    public ArrayList<CallListEntity.DataEntity> getData() {
        return data;
    }

    public static class DataEntity implements Serializable {
        int id;
        boolean isEditing;
        String avatar;
        String name;
        String phone;

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
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

        public void setEditing(boolean editing) {
            isEditing = editing;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getName() {
            return name;
        }

        public boolean isEditing() {
            return isEditing;
        }


    }
}
