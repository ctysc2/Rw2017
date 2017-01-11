package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/1/8.
 */

public class FacusListEntity extends BaseEntity {

    public ArrayList<FacusListEntity.DataEntity> data;

    public ArrayList<FacusListEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<FacusListEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable{
        String header;
        String name;
        int num;

        public String getHeader() {
            return header;
        }

        public String getName() {
            return name;
        }

        public int getNum() {
            return num;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
