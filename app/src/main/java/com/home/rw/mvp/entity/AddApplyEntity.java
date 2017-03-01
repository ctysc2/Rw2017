package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2017/2/22.
 */

public class AddApplyEntity extends BaseEntity{

    public AddApplyEntity.DataEntity data;

    public AddApplyEntity.DataEntity getData() {
        return data;
    }

    public class DataEntity{
        String id;
        String assessor;
        String avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAssessor() {
            return assessor;
        }

        public void setAssessor(String assessor) {
            this.assessor = assessor;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
