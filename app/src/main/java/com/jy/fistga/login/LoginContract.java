package com.jy.fistga.login;

import com.jy.fistga.base.BasePresenter;
import com.jy.fistga.base.BaseView;

/*
 * created by taofu on 2018/8/30
 **/
public interface LoginContract {



    public interface View extends BaseView<LoginContract.Presenter>{

        void verificationCodeSuccess();
        void verificationCodeFail();


        void loginSuccess();
        void loginFail(String msg);




    }



    public  interface Presenter extends BasePresenter<LoginContract.View>{


        void getVerificationCode(String phoneNumber);

        void login(String phoneNumber,String code);



    }

}
