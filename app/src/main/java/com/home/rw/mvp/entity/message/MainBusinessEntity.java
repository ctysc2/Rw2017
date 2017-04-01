package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.LinkedEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.ui.activitys.message.MessageMoreActivity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/17.
 */

public class MainBusinessEntity extends BaseEntity{

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }


    public static class DataEntity{
        private BiPhone biPhones;
        private String lastRwNoticeTime;
        private String lastCoNoticeTime;
        private ArrayList<MessageCommonEntity> favorites;

        public void setLastCoNoticeTime(String lastCoNoticeTime) {
            this.lastCoNoticeTime = lastCoNoticeTime;
        }

        public void setLastRwNoticeTime(String lastRwNoticeTime) {
            this.lastRwNoticeTime = lastRwNoticeTime;
        }

        public String getLastCoNoticeTime() {
            return lastCoNoticeTime;
        }

        public String getLastRwNoticeTime() {
            return lastRwNoticeTime;
        }

        public BiPhone getBiPhones() {
            return biPhones;
        }

        public void setBiPhones(BiPhone biPhones) {
            this.biPhones = biPhones;
        }



        public ArrayList<MessageCommonEntity> getFavorites() {
            return favorites;
        }

        public void setFavorites(ArrayList<MessageCommonEntity> favorites) {
            this.favorites = favorites;
        }

        public static class BiPhone{
            String speakName;
            String speakTime;

            public void setSpeakName(String speakName) {
                this.speakName = speakName;
            }

            public void setSpeakTime(String speakTime) {
                this.speakTime = speakTime;
            }

            public String getSpeakName() {
                return speakName;
            }

            public String getSpeakTime() {
                return speakTime;
            }
        }
    }

}
