package com.home.rw.mvp.entity;

import com.home.rw.mvp.entity.base.BaseEntity;

/**
 * Created by cty on 2017/2/22.
 */

public class UserInfoEntity extends BaseEntity{
    public UserInfoEntity.DataEntity data;

    public UserInfoEntity.DataEntity getData() {
        return data;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "data=" + data +
                '}';
    }

    public static class DataEntity{
        String code;
        String realname;
        String avatar;
        String phone;
        String gender;
        Company company;
        Dept dept;
        String followNum;
        String followCompanyNum;
        String noticeNum;
        String pubNum;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Company getCompany() {
            return company;
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Dept getDept() {
            return dept;
        }

        public void setDept(Dept dept) {
            this.dept = dept;
        }

        public String getFollowNum() {
            return followNum;
        }

        public void setFollowNum(String followNum) {
            this.followNum = followNum;
        }

        public String getFollowCompanyNum() {
            return followCompanyNum;
        }

        public void setFollowCompanyNum(String followCompanyNum) {
            this.followCompanyNum = followCompanyNum;
        }

        public String getNoticeNum() {
            return noticeNum;
        }

        public void setNoticeNum(String noticeNum) {
            this.noticeNum = noticeNum;
        }

        public String getPubNum() {
            return pubNum;
        }

        public void setPubNum(String pubNum) {
            this.pubNum = pubNum;
        }

        public static class Company{
            String id;
            String name;

            public void setName(String name) {
                this.name = name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public static class Dept{
            String id;
            String name;

            public void setName(String name) {
                this.name = name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }


}
