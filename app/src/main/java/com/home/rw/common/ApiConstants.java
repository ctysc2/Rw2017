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

    public static String TOPIC = BASE_URL+"topic/";

    public static String FOCUS = BASE_URL+"focus/";

    public static String BUSINESE = BASE_URL+"business/";

    public static String COMMENT = BASE_URL+"Comment/";

    public static String FRIEND = BASE_URL+"friend/";

    public static String RW_NOTICE = BASE_URL+"rw_notice/";

    public static String COMPANY = BASE_URL+"company/";

    public static String GROUP = BASE_URL+"group/";
        //获取接口类型
        public static String getHost(int hostType) {

            String host;

            switch (hostType) {
                case HostType.LOGIN:
                case HostType.LOGOUT:
                case HostType.UPLOAD:
                case HostType.OUT_LINK1:
                case HostType.OUT_LINK2:
                case HostType.OUT_LINK3:
                case HostType.OUT_LINK4:
                case HostType.TOPIC_DETAIL:
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
                case HostType.FOCUS_LIST:
                case HostType.MY_PUBLISH:
                case HostType.ZAN:
                case HostType.BUSINESS_CALL:
                case HostType.MY_FRIEND_LIST:
                case HostType.ACCEPT_FRIEND:
                case HostType.NEW_FRIEND:
                case HostType.MY_GROUP:
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
                case HostType.TOPIC_PUBLISH:
                    host = TOPIC;
                    break;
                case HostType.FOCUS:
                case HostType.CANCLE_FOCUS:
                    host = FOCUS;
                    break;
                case HostType.PUBLISH_LIST1:
                case HostType.PUBLISH_LIST2:
                case HostType.PUBLISH_LIST3:
                case HostType.PUBLISH_LIST4:
                case HostType.MIX_FOCUS:
                case HostType.OTHER_PUBLISH:
                case HostType.MAIN_PAGE:
                case HostType.DYN:
                case HostType.MAIN_MESSAGE:
                    host = BUSINESE;
                    break;
                case HostType.TOPIC_FEEDBACK:
                    host = COMMENT;
                    break;
                case HostType.ADD_FRIEND:
                case HostType.REMARK:
                    host = FRIEND;
                    break;
                case HostType.RW_NOTICE:
                    host = RW_NOTICE;
                    break;
                case HostType.COMPANY_NOTICE:
                case HostType.COMPANY_NOTICE_READ:
                case HostType.DEPARTMENT:
                    host = COMPANY;
                    break;
                case HostType.ADD_GROUP:
                    host = GROUP;
                    break;
                case HostType.OTHER_USER:
                    host = USER;
                    break;
                default:
                    host="";
                    break;
            }
            return host;
        }
}
