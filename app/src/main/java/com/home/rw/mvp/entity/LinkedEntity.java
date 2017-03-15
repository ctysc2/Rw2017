package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/10.
 */

public class LinkedEntity extends BaseEntity{
    private ArrayList<DataEntity> data;

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public static class DataEntity{
        private String id;
        private String imgs;
        private String title;
        private String toLink;
        private String type;


        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getToLink() {
            return toLink;
        }

        public void setToLink(String toLink) {
            this.toLink = toLink;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
