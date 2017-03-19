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

    public static class BiPhone{

    }

    public static class LastRwNoticeTime{

    }

    public static class LastCoNoticeTime{

    }

    public static class DataEntity{
        private BiPhone biPhones;
        private LastRwNoticeTime lastRwNoticeTime;
        private LastCoNoticeTime lastCoNoticeTime;
        private ArrayList<MessageMoreActivity> favorites;

        public BiPhone getBiPhones() {
            return biPhones;
        }

        public void setBiPhones(BiPhone biPhones) {
            this.biPhones = biPhones;
        }

        public LastRwNoticeTime getLastRwNoticeTime() {
            return lastRwNoticeTime;
        }

        public void setLastRwNoticeTime(LastRwNoticeTime lastRwNoticeTime) {
            this.lastRwNoticeTime = lastRwNoticeTime;
        }

        public LastCoNoticeTime getLastCoNoticeTime() {
            return lastCoNoticeTime;
        }

        public void setLastCoNoticeTime(LastCoNoticeTime lastCoNoticeTime) {
            this.lastCoNoticeTime = lastCoNoticeTime;
        }

        public ArrayList<MessageMoreActivity> getFavorites() {
            return favorites;
        }

        public void setFavorites(ArrayList<MessageMoreActivity> favorites) {
            this.favorites = favorites;
        }
    }

}
