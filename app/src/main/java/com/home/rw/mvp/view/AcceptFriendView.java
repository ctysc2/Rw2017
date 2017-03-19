package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/17.
 */

public interface AcceptFriendView extends BaseView{
    void acceptFriendCompleted(BaseEntity data);
}
