package com.home.rw.common;

/**
 * Created by cty on 16/12/09.
 */
public class ApiConstants {

    public static String BASE_URL = "http://www.jskyme.cn:8080/rw/api/";

    public static String USER = BASE_URL+"user/";

    public static String MY = BASE_URL+"my/";

    public static String LEAVE = BASE_URL+"leave/";

    public static String EXPENSE = BASE_URL+"expense/";

    public static String OUTGO = BASE_URL+"outgo/";

    public static String OVERTIME = BASE_URL+"overtime/";

    public static String APPLY = BASE_URL+"apply/";

    public static String WORK_LOG = BASE_URL+"work_log/";

    public static String ROLL = BASE_URL+"work_item/";

    public static String SIGN = BASE_URL+"sign_in/";

    public static String CARD = BASE_URL+"timecard/";
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
                case HostType.USER_INFO:
                case HostType.APPROVE_CHECKED:
                case HostType.APPROVE_WAITINNG_CHECKED:
                case HostType.APPROVE_CHECKING:
                case HostType.APPROVE_PASSED:
                case HostType.APPROVE_REJECT:
                case HostType.MODIFI_USER_INFO:
                case HostType.VERIFI_CODE:
                case HostType.MODIFI_PASSWORD:
                case HostType.MY_FEEDBACK:
                    host = MY;
                    break;
                case HostType.ADD_APPLY_EXPENSE:
                case HostType.EDIT_APPLY_EXPENSE:
                case HostType.DETAIL_APPLY_EXPENSE:
                    host = EXPENSE;
                    break;
                case HostType.ADD_APPLY_OUTGO:
                case HostType.EDIT_APPLY_OUTGO:
                case HostType.DETAIL_APPLY_OUTGO:
                    host = OUTGO;
                    break;
                case HostType.ADD_APPLY_LEAVE:
                case HostType.EDIT_APPLY_LEAVE:
                case HostType.DETAIL_APPLY_LEAVE:
                    host = LEAVE;
                    break;
                case HostType.ADD_APPLY_OVERTIME:
                case HostType.EDIT_APPLY_OVERTIME:
                case HostType.DETAIL_APPLY_OVERTIME:
                    host = OVERTIME;
                    break;
                case HostType.APPLY_DO_PASS:
                case HostType.APPLY_DO_REJECT:
                    host = APPLY;
                    break;
                case HostType.WRITE_LOG:
                case HostType.SEND_LOG:
                case HostType.RECEIVE_LOG:
                    host = WORK_LOG;
                    break;
                case HostType.SEND_ROLL:
                case HostType.RECEIVE_ROLL:
                    host = ROLL;
                    break;
                case HostType.SIGN:
                case HostType.SIGN_LIST:
                    host = SIGN;
                    break;
                case HostType.CARD:
                case HostType.CARD_QUERY:
                    host = CARD;
                    break;
                default:
                    host="";
                    break;
            }
            return host;
        }
}
