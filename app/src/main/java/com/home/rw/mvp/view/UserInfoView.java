package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.UserInfoEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/2/22.
 */

public interface UserInfoView extends BaseView {
    void getUserInfoCompleted(UserInfoEntity data);
}
