package com.jy.fistga.data.source;

import android.arch.lifecycle.LifecycleObserver;

import com.jy.fistga.data.User;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.Map;

import io.reactivex.Observer;

/*
 * created by taofu on 2018/8/29
 **/
public interface UserDataSource {



   void login(RxFragment fragment, Map<String, String> params, Observer<User> observer);

   void sendVerificationCode(RxFragment fragment, Map<String, String> params, Observer<Object> observer);


   void uploadAvatar(RxFragment fragment,String filePath,String userId,Observer<Object> objectObserver);

   void release();


   void saveUserTodB(User user);

  void  getUserFromDb(LifecycleProvider lifecycleProvider, Observer<User> observer);

}
