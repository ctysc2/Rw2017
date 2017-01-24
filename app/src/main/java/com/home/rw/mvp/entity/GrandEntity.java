package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/18.
 */

public class GrandEntity extends BaseEntity{
    public ArrayList<GrandEntity.DataEntity> data;

    public ArrayList<GrandEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<GrandEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        String date;
        String detailTitle;
        String title;
        String img;
        String content;

        public void setDetailTitle(String detailTitle) {
            this.detailTitle = detailTitle;
        }

        public String getDetailTitle() {
            return detailTitle;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getContent() {
            return content;
        }

        public String getImg() {
            return img;
        }
    }
}
