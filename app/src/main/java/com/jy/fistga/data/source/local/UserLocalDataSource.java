package com.jy.fistga.data.source.local;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.jy.fistga.data.User;
import com.jy.fistga.data.source.UserDataSource;
import com.jy.fistga.data.source.local.greendao.DaoSession;
import com.jy.fistga.data.source.local.greendao.Greendao;
import com.jy.fistga.data.source.local.greendao.UserDao;
import com.jy.fistga.data.source.remote.UserRemoteDataSource;
import com.jy.fistga.utils.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.trello.rxlifecycle2.internal.Preconditions;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by taofu on 2018/9/5
 **/
public class UserLocalDataSource implements UserDataSource{

    private static UserLocalDataSource INSTANCE;

    private UserDao mUserDao;
    private DaoSession mDaoSession;
    private UserLocalDataSource(Context context){
        mDaoSession = Greendao.getDaoSession(context);
        mUserDao =  mDaoSession.getUserDao();
    }

    public static synchronized  UserLocalDataSource getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new UserLocalDataSource(context);
        }

        return INSTANCE;
    }
    @Override
    public void login(RxFragment fragment, Map<String, String> params, Observer<User> observer) {

    }

    @Override
    public void sendVerificationCode(RxFragment fragment, Map<String, String> params, Observer<Object> observer) {

    }

    @Override
    public void uploadAvatar(RxFragment fragment, String filePath, String userId, Observer<Object> objectObserver) {

    }

    @Override
    public void release() {

    }

    @Override
    public void saveUserTodB(User user) {

        mUserDao.insert(user);

    }
    @Override
    public void getUserFromDb(LifecycleProvider lifecycleProvider, Observer<User> observer) {
        buildObserver( Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {

                List<User> users = mUserDao.loadAll();

                if(users != null && users.size() > 0){
                    emitter.onNext(users.get(0));
                }else{
                    emitter.onNext(null);
                }
            }
        }), observer, lifecycleProvider);

    }



    private <T> void buildObserver(Observable<T> observable,Observer<T> observer,LifecycleProvider lifecycleProvider){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose((lifecycleProvider instanceof RxAppCompatActivity) ?(((RxAppCompatActivity)lifecycleProvider).<T>bindUntilEvent(ActivityEvent.DESTROY)) :  (((RxFragment)lifecycleProvider).<T>bindUntilEvent(FragmentEvent.DETACH)))
                .subscribe(observer);
    }



   /* @Override
    public <T> void getUserFromDb(LifecycleProvider<T> lifecycleProvider, Observer<T> observer) {
        Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {

                List<User> users = mUserDao.loadAll();

                if(users != null && users.size() > 0){
                    emitter.onNext(users.get(0));
                }else{
                    emitter.onNext(null);
                }


            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose( (lifecycleProvider instanceof RxAppCompatActivity) ?(((RxAppCompatActivity)lifecycleProvider).bindUntilEvent(ActivityEvent.DESTROY)) :  (((RxFragment)lifecycleProvider).bindUntilEvent(FragmentEvent.DETACH)))
                .subscribe();

    }*/

/*
    @Override
    public void getUserFromDb(final LifecycleProvider lifecycleProvider, final Observer<User> observer) {
        if(mDaoSession == null || mUserDao == null){
            observer.onNext(null);
        }

        mDaoSession.startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<User> users = mUserDao.loadAll();
              // if(fragment.isAdded() && !fragment.isRemoving()){
                   if(users != null && users.size() > 0){
                       observer.onNext(users.get(0));
                   }else{
                       observer.onNext(null);
                   }
               //}

            }
        });






    }*/
}
