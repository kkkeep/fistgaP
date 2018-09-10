package com.jy.fistga.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jy.fistga.base.BaseActivity;
import com.jy.fistga.R;
import com.jy.fistga.data.source.UserDataRepository;
import com.jy.fistga.data.source.local.UserLocalDataSource;
import com.jy.fistga.data.source.remote.UserRemoteDataSource;
import com.jy.fistga.login.register.FragmentRegister;
import com.jy.fistga.login.register.RegisterContact;
import com.jy.fistga.login.register.RegisterPresenter;
import com.jy.fistga.utils.StatusBarManager;

/*
 * created by taofu on 2018/8/30
 **/
public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        StatusBarManager.lightStatusBar(this);
        LoginPresenter loginPresenter = new LoginPresenter(UserDataRepository.getInstance(UserRemoteDataSource.getInstance(), UserLocalDataSource.getInstance(this)));
        addFragment(FragmentLogin.class, loginPresenter, R.id.login_container, null, null);



    }


}
