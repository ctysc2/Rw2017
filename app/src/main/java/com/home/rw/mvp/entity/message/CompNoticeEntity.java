package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/17.
 */

public class CompNoticeEntity extends BaseEntity{

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity extends BaseApprovementEntity{

        ArrayList<TopicCommonEntity> resLst;

        public void setResLst(ArrayList<TopicCommonEntity> resLst) {
            this.resLst = resLst;
        }

        public ArrayList<TopicCommonEntity> getResLst() {
            return resLst;
        }
    }
}
