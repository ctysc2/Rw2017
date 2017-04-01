package io.rong.callkit;

/**
 * Created by cty on 2017/3/20.
 */

public class OutGoingNotice {
    private String userId;
    public OutGoingNotice(String userId){
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
