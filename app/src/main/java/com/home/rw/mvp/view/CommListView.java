package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/10.
 */

public interface CommListView extends BaseView{
    void getCommListCompleted(CommunicationEntity data);
}
