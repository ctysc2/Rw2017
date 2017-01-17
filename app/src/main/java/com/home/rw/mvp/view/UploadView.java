package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.entity.UploadEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/1/12.
 */

public interface UploadView extends BaseView {
    void uploadComplete(UploadEntity data);
}
