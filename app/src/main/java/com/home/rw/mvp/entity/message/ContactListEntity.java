package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/19.
 */

public class ContactListEntity extends BaseEntity{
    private ArrayList<MessageCommonEntity>  data;

    public void setData(ArrayList<MessageCommonEntity> data) {
        this.data = data;
    }

    public ArrayList<MessageCommonEntity> getData() {
        return data;
    }


}
