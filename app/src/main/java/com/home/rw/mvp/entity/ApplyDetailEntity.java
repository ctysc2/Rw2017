package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cty on 2017/2/28.
 */

public class ApplyDetailEntity extends BaseEntity{

    public DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity{
        String id;
        String assessor;
        String avatar;
        String content;
        String extra;
        String imgs;
        String details;
        String beginTime;
        String endTime;
        String workType;
        String length;

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }


        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getWorkType() {
            return workType;
        }

        public void setWorkType(String workType) {
            this.workType = workType;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public static class Detail{
            int amount;
            String expenseType;
            String remark;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getExpenseType() {
                return expenseType;
            }

            public void setExpenseType(String expenseType) {
                this.expenseType = expenseType;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }


    }
}
