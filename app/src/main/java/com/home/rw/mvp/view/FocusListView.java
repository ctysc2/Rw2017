package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/9.
 */

public interface FocusListView extends BaseView{
    void getFocusList(FacusListEntity data);
}
