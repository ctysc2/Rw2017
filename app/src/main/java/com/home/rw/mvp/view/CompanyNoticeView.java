package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.CompanyNoticeEntity;
import com.home.rw.mvp.entity.message.CompNoticeEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/17.
 */

public interface CompanyNoticeView extends BaseView {
    void getCompanyNoticeCompleted(CompNoticeEntity data);
}
