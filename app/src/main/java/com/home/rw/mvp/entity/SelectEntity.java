package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/2/3.
 */

public class SelectEntity extends BaseEntity {
    public ArrayList<SelectEntity.DataEntity> data;

    public ArrayList<SelectEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<SelectEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        private int id;
        private String avatar;
        private String name;
        private boolean isSelected;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getName() {
            return name;
        }

        public boolean isSelected() {
            return isSelected;
        }
    }
}
