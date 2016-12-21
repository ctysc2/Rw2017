package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2016/12/12.
 */

public interface LoginView extends BaseView{
    void loginCompleted(LoginEntity data);
}
