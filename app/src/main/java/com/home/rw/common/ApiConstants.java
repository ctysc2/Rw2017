package com.home.rw.common;

/**
 * Created by cty on 16/12/09.
 */
public class ApiConstants {

        public static String VERSIONCODE="v2.2";

        public static String BASE_URL = "http://www.jskyme.cn:8080/rw/api/";


        //获取接口类型
        public static String getHost(int hostType) {

            String host;

            switch (hostType) {
                case HostType.LOGIN:
                    host = BASE_URL;
                    break;
                default:
                    host = "";
                    break;
            }
            return host;
        }
}
