package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.message.MyFriendEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/17.
 */

public interface MyFriendView extends BaseView{
    void getMyFriendCompleted(MyFriendEntity data);
}
