package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2016/12/28.
 */

public class RollMeEntity extends BaseEntity {

    public ArrayList<RollMeEntity.DataEntity> data;

    public ArrayList<RollMeEntity.DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<RollMeEntity.DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity{
        String sender;
        String sendTime;
        String deadLineTime;
        String content;
        boolean isExpand = false;

        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setDeadLineTime(String deadLineTime) {
            this.deadLineTime = deadLineTime;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getContent() {
            return content;
        }

        public String getDeadLineTime() {
            return deadLineTime;
        }

        public String getSender() {
            return sender;
        }

        public String getSendTime() {
            return sendTime;
        }


    }
}
