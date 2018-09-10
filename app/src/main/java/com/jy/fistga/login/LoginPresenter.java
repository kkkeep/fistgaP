package com.jy.fistga.login;

import com.jy.fistga.base.FirstApplication;
import com.jy.fistga.data.User;
import com.jy.fistga.data.source.UserDataSource;
import com.jy.fistga.exception.ServerException;
import com.jy.fistga.utils.Logger;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/*
 * created by taofu on 2018/8/30
 **/
public class LoginPresenter implements LoginContract.Presenter{

    private LoginContract.View mView;

    private UserDataSource mUserDataRepository;

    public LoginPresenter(UserDataSource userDataRepository){

        mUserDataRepository = userDataRepository;
    }

    @Override
    public void getVerificationCode(String phoneNumber) {


        Map<String,String> params = new HashMap<>();

        params.put("phone", phoneNumber);
        params.put("smsType", "0");

        mUserDataRepository.sendVerificationCode((RxFragment) mView, params, new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("onSubscribe");
            }

            @Override
            public void onNext(Object o) {

               mView.verificationCodeSuccess();
            }

            @Override
            public void onError(Throwable e) {
                mView.verificationCodeFail();
                Logger.d("onError");
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        });
    }

    @Override
    public void login(String phoneNumber, String code) {

        Map<String,String> params = new HashMap<>();
        params.put("phone", phoneNumber);
        params.put("verificationCode", code);
        params.put("platform", "1");
        params.put("appVersion", "1.0.0");
        params.put("deviceId", "1454564545");
        mUserDataRepository.login((RxFragment)mView, params, new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                ((FirstApplication)mView.getActivity().getApplication()).setUser(user);
                mUserDataRepository.saveUserTodB(user);
                mView.loginSuccess();
            }

            @Override
            public void onError(Throwable e) {

                mView.loginFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Override
    public void attachView(LoginContract.View baseView) {
        mView = baseView;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
