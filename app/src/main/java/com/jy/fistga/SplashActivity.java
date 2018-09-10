package com.jy.fistga;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jy.fistga.base.BaseActivity;
import com.jy.fistga.base.MyObserver;
import com.jy.fistga.data.User;
import com.jy.fistga.data.source.UserDataRepository;
import com.jy.fistga.data.source.UserDataSource;
import com.jy.fistga.data.source.local.UserLocalDataSource;
import com.jy.fistga.data.source.remote.UserRemoteDataSource;
import com.jy.fistga.login.LoginActivity;
import com.jy.fistga.login.LoginContract;
import com.jy.fistga.utils.Logger;
import com.jy.fistga.utils.StatusBarManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by taofu on 2018/9/4
 **/
public class SplashActivity extends BaseActivity implements LoginContract.View{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //StatusBarManager.translucentStatusBarAndImmersive(this);
        //StatusBarManager.hideNavigationBar(this);

        //StatusBarManager.translucentStatusBarAndImmersive(this);
        StatusBarManager.setStatusBarColor(this, Color.RED);


       UserDataSource userDataSource =  UserDataRepository.getInstance(UserRemoteDataSource.getInstance(), UserLocalDataSource.getInstance(this));

       userDataSource.getUserFromDb(this, new MyObserver<User>(){
           @Override
           public void onNext(final User user) {
               getWindow().getDecorView().post(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(SplashActivity.this, Logger.toStringOb(user), 1).show();

                       startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                   }
               });


           }

           @Override
           public void onError(Throwable e) {
               super.onError(e);
           }
       });


        Observable.just(1).map(new Function<Integer, Object>() {
            @Override
            public Object apply(Integer integer) throws Exception {
                    return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Logger.d("%s", "1");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("%s", "1");
            }

            @Override
            public void onComplete() {
                Logger.d("%s", "1");
            }
        });


    }

    @Override
    public void verificationCodeSuccess() {

    }

    @Override
    public void verificationCodeFail() {

    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail(String msg) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public Activity getActivity() {
        return null;
    }
}
