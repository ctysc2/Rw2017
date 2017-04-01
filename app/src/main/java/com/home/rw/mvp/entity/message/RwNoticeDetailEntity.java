package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2017/3/20.
 */

public class RwNoticeDetailEntity extends BaseEntity{

    private TopicCommonEntity data;

    public void setData(TopicCommonEntity data) {
        this.data = data;
    }

    public TopicCommonEntity getData() {
        return data;
    }
}
