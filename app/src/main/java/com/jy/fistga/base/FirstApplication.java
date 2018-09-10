package com.jy.fistga.base;

import android.app.Application;

import com.jy.fistga.data.User;

/*
 * created by taofu on 2018/8/29
 **/
public class FirstApplication extends Application {


    private User mUser;
    @Override
    public void onCreate() {
        super.onCreate();
    }


    public User getUser(){
        return mUser;
    }

    public void setUser(User user){
        mUser = user;
    }

}
