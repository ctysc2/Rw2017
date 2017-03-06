package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.SignEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/2.
 */

public interface SignListView extends BaseView{
    void getSignListComplete(SignEntity data);
}
