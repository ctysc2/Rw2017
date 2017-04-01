package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/17.
 */

public class MyFriendEntity extends BaseEntity{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }


    public static class DataEntity{

        private String waitAppFriendNum;

        private ArrayList<MessageCommonEntity> friends;

        public void setWaitAppFriendNum(String waitAppFriendNum) {
            this.waitAppFriendNum = waitAppFriendNum;
        }

        public String getWaitAppFriendNum() {
            return waitAppFriendNum;
        }

        public void setFriends(ArrayList<MessageCommonEntity> friends) {
            this.friends = friends;
        }

        public ArrayList<MessageCommonEntity> getFriends() {
            return friends;
        }
    }
}
