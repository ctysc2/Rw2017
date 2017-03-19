package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.message.BusineseCallEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/17.
 */

public interface BusinessCallView extends BaseView{
    void getBusinessCallCompleted(BusineseCallEntity data);
}
