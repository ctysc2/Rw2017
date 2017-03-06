package com.home.rw.mvp.view;

import com.home.rw.mvp.entity.CardQueryEntity;
import com.home.rw.mvp.view.base.BaseView;

/**
 * Created by cty on 2017/3/2.
 */

public interface CardQueryView extends BaseView{
    void getCardQueryCompleted(CardQueryEntity data);
}
