package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.message.ContactListEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/19.
 */

public interface UserByPhoneView extends BaseView{
    void getUserByPhoneCompleted(ContactListEntity data);
}
