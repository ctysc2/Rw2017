package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseApprovementEntity;
import com.home.rw.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2017/3/17.
 */

public class MyGroupEntity extends BaseEntity{

    public static class DataEntity extends BaseApprovementEntity{
        public static class ResLst{
            private String id;
            private String name;
            private String personNum;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPersonNum() {
                return personNum;
            }

            public void setPersonNum(String personNum) {
                this.personNum = personNum;
            }
        }
    }

}
