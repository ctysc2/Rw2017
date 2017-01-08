package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/6.
 */

public class CommunicationEntity extends BaseEntity {
    public ArrayList<CommunicationEntity.DataEntity> data;

    public ArrayList<CommunicationEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<CommunicationEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{

        private static final long serialVersionUID = 1L;

        ArrayList<String> imgs = new ArrayList<>();
        String header;
        String name;
        String title;
        String  content;
        long time;
        int zanNum;
        boolean isZaned;
        boolean isFacused;

        public long getTime() {
            return time;
        }

        public String getTitle() {
            return title;
        }

        public int getZanNum() {
            return zanNum;
        }

        public String getName() {
            return name;
        }

        public String getHeader() {
            return header;
        }

        public String getContent() {
            return content;
        }

        public ArrayList<String> getImgs() {
            return imgs;
        }

        public boolean isFacused() {
            return isFacused;
        }

        public boolean isZaned() {
            return isZaned;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setFacused(boolean facused) {
            isFacused = facused;
        }

        public void setZaned(boolean zaned) {
            isZaned = zaned;
        }

        public void setImgs(ArrayList<String> imgs) {
            this.imgs = imgs;
        }

        public void setZanNum(int zanNum) {
            this.zanNum = zanNum;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}
