package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.entity.message.CreatGroupEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/18.
 */

public interface AddGroupView extends BaseView{
    void addGroupCompleted(CreatGroupEntity data);
}
