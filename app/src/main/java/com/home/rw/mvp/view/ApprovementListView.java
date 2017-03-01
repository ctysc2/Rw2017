package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.AddApplyEntity;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/2/24.
 */

public interface ApprovementListView extends BaseView {
    void getApprovementListCompleted(ApprovementListEntity data);
}
