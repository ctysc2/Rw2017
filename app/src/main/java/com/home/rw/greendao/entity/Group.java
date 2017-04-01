package com.home.rw.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cty on 2017/3/31.
 */
@Entity
public class Group {
    @Id
    private Long id;
    private String groupName;
    private String groupNum;
    public String getGroupNum() {
        return this.groupNum;
    }
    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }
    public String getGroupName() {
        return this.groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 645612603)
    public Group(Long id, String groupName, String groupNum) {
        this.id = id;
        this.groupName = groupName;
        this.groupNum = groupNum;
    }
    @Generated(hash = 117982048)
    public Group() {
    }
}
