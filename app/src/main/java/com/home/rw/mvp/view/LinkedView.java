package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.LinkedEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/10.
 */

public interface LinkedView extends BaseView{
    void getLinkedCompleted(int reqType,LinkedEntity data);
}
