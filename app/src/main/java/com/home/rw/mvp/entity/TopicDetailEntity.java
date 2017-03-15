package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2017/3/11.
 */

public class TopicDetailEntity extends BaseEntity{
    private CommunicationEntity.DataEntity.ResLst data;

    public void setData(CommunicationEntity.DataEntity.ResLst data) {
        this.data = data;
    }

    public CommunicationEntity.DataEntity.ResLst getData() {
        return data;
    }

}
