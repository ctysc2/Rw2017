package com.home.rw.common;

/**
 * Created by cty on 16/12/09.
 */
public class ApiConstants {

        public static String BASE_URL = "http://www.jskyme.cn:8080/rw/api/";

    public static String USER = BASE_URL+"user/";


        //获取接口类型
        public static String getHost(int hostType) {

            String host;

            switch (hostType) {
                case HostType.LOGIN:
                    host = BASE_URL;
                    break;
                case HostType.UPLOAD:
                    host = BASE_URL;
                    break;
                case HostType.AVATAR:
                    host = USER;
                    break;
                default:
                    host = "";
                    break;
            }
            return host;
        }
}
