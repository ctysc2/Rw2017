package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/19.
 */

public class ContactListEntity extends BaseEntity{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity{
        ArrayList<MessageCommonEntity> friends;

        public void setFriends(ArrayList<MessageCommonEntity> friends) {
            this.friends = friends;
        }

        public ArrayList<MessageCommonEntity> getFriends() {
            return friends;
        }
    }
}
