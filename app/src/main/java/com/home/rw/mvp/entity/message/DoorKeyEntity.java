package com.home.rw.mvp.entity.message;

import java.util.ArrayList;

/**
 * Created by cty on 2017/3/31.
 */

public class DoorKeyEntity {
    private int Code;
    private String Message;
    private Value Value;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public DoorKeyEntity.Value getValue() {
        return Value;
    }

    public void setValue(DoorKeyEntity.Value value) {
        Value = value;
    }

    public static class Value{

        private ArrayList<BlueKeys> BlueKeys;

        private ArrayList<EmployeeKeysInfo> EmployeeKeysInfo;

        public void setEmployeeKeysInfo(ArrayList<DoorKeyEntity.Value.EmployeeKeysInfo> employeeKeysInfo) {
            EmployeeKeysInfo = employeeKeysInfo;
        }

        public ArrayList<DoorKeyEntity.Value.EmployeeKeysInfo> getEmployeeKeysInfo() {
            return EmployeeKeysInfo;
        }

        public void setBlueKeys(ArrayList<DoorKeyEntity.Value.BlueKeys> blueKeys) {
            BlueKeys = blueKeys;
        }

        public ArrayList<DoorKeyEntity.Value.BlueKeys> getBlueKeys() {
            return BlueKeys;
        }

        public static class  EmployeeKeysInfo{
            int DeviceId;
            int EmployeeId;
            int EmployeeKeyId;
            String EndTime;
            int GroupId;
            String GroupName;
            String Name;
            String StartTime;

            public int getDeviceId() {
                return DeviceId;
            }

            public void setDeviceId(int deviceId) {
                DeviceId = deviceId;
            }

            public int getEmployeeId() {
                return EmployeeId;
            }

            public void setEmployeeId(int employeeId) {
                EmployeeId = employeeId;
            }

            public int getEmployeeKeyId() {
                return EmployeeKeyId;
            }

            public void setEmployeeKeyId(int employeeKeyId) {
                EmployeeKeyId = employeeKeyId;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String endTime) {
                EndTime = endTime;
            }

            public int getGroupId() {
                return GroupId;
            }

            public void setGroupId(int groupId) {
                GroupId = groupId;
            }

            public String getGroupName() {
                return GroupName;
            }

            public void setGroupName(String groupName) {
                GroupName = groupName;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String startTime) {
                StartTime = startTime;
            }
        }
        public static class BlueKeys{
            String bluePwd;
            String code;
            String companyCode;
            String deviceId;
            String endTime;
            String mac;
            int rssiMin;
            String startTime;

            public String getBluePwd() {
                return bluePwd;
            }

            public void setBluePwd(String bluePwd) {
                this.bluePwd = bluePwd;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCompanyCode() {
                return companyCode;
            }

            public void setCompanyCode(String companyCode) {
                this.companyCode = companyCode;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getMac() {
                return mac;
            }

            public void setMac(String mac) {
                this.mac = mac;
            }

            public int getRssiMin() {
                return rssiMin;
            }

            public void setRssiMin(int rssiMin) {
                this.rssiMin = rssiMin;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
        }
    }
}
