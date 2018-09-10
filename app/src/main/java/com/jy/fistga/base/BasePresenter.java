package com.jy.fistga.base;

import android.app.Activity;

/*
 * created by taofu on 2018/8/30
 **/
public interface BasePresenter<T extends BaseView> {

    void attachView(T baseView);

    void detachView();



}
