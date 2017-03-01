package com.home.rw.mvp.view.base;

/**
 * Created by cty on 16/10/18.
 */
public interface BaseView {
    void showProgress(int reqType);

    void hideProgress(int reqType);

    void showErrorMsg(int reqType,String msg);
}
