package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2016/12/19.
 */

public class CarouselResponseEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public ArrayList<DataEntity> data;

    public static class DataEntity {
        public String id;
        public String carouselId;
        public String description;
        public String updateTime;
        public int status;
        public String title;
        public String link;
        public int linkType;
        public String page;
        public String offline;

        @Override
        public String toString() {
            return "DataEntity{" +
                    "id='" + id + '\'' +
                    ", carouselId='" + carouselId + '\'' +
                    ", description='" + description + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", status=" + status +
                    ", title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", linkType=" + linkType +
                    ", page='" + page + '\'' +
                    ", offline='" + offline + '\'' +
                    '}';
        }
    }
}
