package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.MineNoticeEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/5/3.
 */

public interface MineNoticeView extends BaseView{
    void getMineNoticeCompleted(MineNoticeEntity data);
}
