package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/2/22.
 */

public interface AddApplyView extends BaseView{
    void addApplyCompleted(AddApplyEntity data);
}
