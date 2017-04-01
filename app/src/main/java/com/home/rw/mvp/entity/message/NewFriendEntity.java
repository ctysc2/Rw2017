package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/17.
 */

public class NewFriendEntity extends BaseEntity{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity extends BaseApprovementEntity{

        private ArrayList<ResLst> resLst;

        public void setResLst(ArrayList<ResLst> resLst) {
            this.resLst = resLst;
        }

        public ArrayList<ResLst> getResLst() {
            return resLst;
        }

        public static class ResLst extends MessageCommonEntity{
            String remark;

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getRemark() {
                return remark;
            }
        }
    }
}
