package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.message.DepartmentEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/18.
 */

public interface DepartmentView extends BaseView{
    void getDepartmentListCompleted(DepartmentEntity data);
}
