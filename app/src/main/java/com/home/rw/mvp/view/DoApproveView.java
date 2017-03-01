package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/2/25.
 */

public interface DoApproveView extends BaseView {
    void doApproveCompleted(BaseEntity data);
}
