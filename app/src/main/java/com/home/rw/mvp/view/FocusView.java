package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/10.
 */

public interface FocusView extends BaseView{
    void doFocusCompleted(BaseEntity data);
}
