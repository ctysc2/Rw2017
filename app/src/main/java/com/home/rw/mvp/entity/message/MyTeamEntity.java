package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.LinkedEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/21.
 */

public class MyTeamEntity extends BaseEntity{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity{
        String name;
        String logo;
        String leaderName;
        String personNum;
        ArrayList<MessageCommonEntity> persons;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLeaderName() {
            return leaderName;
        }

        public void setLeaderName(String leaderName) {
            this.leaderName = leaderName;
        }

        public String getPersonNum() {
            return personNum;
        }

        public void setPersonNum(String personNum) {
            this.personNum = personNum;
        }

        public ArrayList<MessageCommonEntity> getPersons() {
            return persons;
        }

        public void setPersons(ArrayList<MessageCommonEntity> persons) {
            this.persons = persons;
        }
    }
}
