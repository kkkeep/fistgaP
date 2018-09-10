package com.jy.fistga.base;

import android.app.Activity;

/*
 * created by taofu on 2018/8/30
 **/
public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

    Activity getActivity();

}
