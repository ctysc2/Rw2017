package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.MainPageEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/12.
 */

public interface MainPageView extends BaseView{
    void getMainPageCompleted(MainPageEntity data);
}
