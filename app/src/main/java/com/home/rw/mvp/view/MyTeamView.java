package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.message.MyTeamEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/21.
 */

public interface MyTeamView extends BaseView{
    void getMyTeamCompleted(MyTeamEntity data);
}
