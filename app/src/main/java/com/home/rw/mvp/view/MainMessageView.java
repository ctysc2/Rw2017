package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.message.MainBusinessEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/17.
 */

public interface MainMessageView extends BaseView{
    void mainMessageCompleted(MainBusinessEntity data);
}
