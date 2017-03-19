package com.home.rw.mvp.entity.message;

import com.home.rw.mvp.entity.base.BaseEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/18.
 */

public class DepartmentEntity extends BaseEntity{

    ArrayList<DataEntity> data;

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public static class DataEntity{
        String id;
        String personNum;
        String name;
        ArrayList<Employees> employees;
        public static class Employees{
            String id;
            String username;
            String realname;
            String avatar;
            String phone;
            String gender;
            Company company;
            Dept dept;
            String focusNum;
            String noticeNum;
            String pubNum;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
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

            public String getFocusNum() {
                return focusNum;
            }

            public void setFocusNum(String focusNum) {
                this.focusNum = focusNum;
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
                String logo;
                String regDate;
                String regCity;
                String intro;

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

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }

                public String getRegDate() {
                    return regDate;
                }

                public void setRegDate(String regDate) {
                    this.regDate = regDate;
                }

                public String getRegCity() {
                    return regCity;
                }

                public void setRegCity(String regCity) {
                    this.regCity = regCity;
                }

                public String getIntro() {
                    return intro;
                }

                public void setIntro(String intro) {
                    this.intro = intro;
                }
            }
            public static class Dept{
                String id;
                String name;

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
            }
        }
    }
}
