package com.home.rw.mvp.view.base;

/**
 * Created by cty on 16/10/18.
 */
public interface BaseView {
    void showProgress();

    void hideProgress();

    void showErrorMsg(String msg);
}
