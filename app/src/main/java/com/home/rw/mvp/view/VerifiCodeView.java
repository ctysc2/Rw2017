package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/5.
 */

public interface VerifiCodeView extends BaseView{
    void sendVerifiCodeCompleted(BaseEntity data);
}
