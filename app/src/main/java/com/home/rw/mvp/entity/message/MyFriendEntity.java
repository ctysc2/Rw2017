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
        private int waiAapprove;
        private ArrayList<MessageCommonEntity> resLst;

        public void setWaiAapprove(int waiAapprove) {
            this.waiAapprove = waiAapprove;
        }

        public int getWaiAapprove() {
            return waiAapprove;
        }



        public void setResLst(ArrayList<MessageCommonEntity> resLst) {
            this.resLst = resLst;
        }

        public ArrayList<MessageCommonEntity> getResLst() {
            return resLst;
        }

    }
}
