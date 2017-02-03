package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cty on 2017/2/3.
 */

public class GroupChatEntity extends BaseEntity{
    public ArrayList<GroupChatEntity.DataEntity> data;

    public ArrayList<GroupChatEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<GroupChatEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity{
        private String name;
        private int num;

        public void setName(String name) {
            this.name = name;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public int getNum() {
            return num;
        }
    }
}
