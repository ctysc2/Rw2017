package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2017/3/19.
 */

public class CreatGroupEntity extends BaseEntity{

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity{
        String id;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
