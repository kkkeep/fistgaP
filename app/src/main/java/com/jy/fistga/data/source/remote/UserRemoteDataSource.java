package com.jy.fistga.data.source.remote;

import com.jy.fistga.data.HttpResult;
import com.jy.fistga.data.User;
import com.jy.fistga.data.source.UserDataSource;
import com.jy.fistga.data.source.remote.retrofit.DataRetrofit;
import com.jy.fistga.data.source.remote.retrofit.FirstgaService;
import com.jy.fistga.exception.ServerException;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/*
 * created by taofu on 2018/8/29
 **/
public class UserRemoteDataSource implements UserDataSource {

    private FirstgaService service;

    private static UserRemoteDataSource INSTANCE;

    private UserRemoteDataSource() {
        service = DataRetrofit.getRetrofitService();

    }


    public static synchronized UserRemoteDataSource getInstance(){

        if(INSTANCE == null){
            INSTANCE = new UserRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void login(RxFragment fragment, Map<String, String> params, Observer<User> observer) {

        Observable<HttpResult<User>> observable = service.login(params);


        observable.flatMap(new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> userHttpResult) throws Exception {

                if (userHttpResult.getCode() == 0) {
                  return Observable.just(userHttpResult.getData());
                }
                return Observable.error(new ServerException(userHttpResult.getCode(),userHttpResult.getMessage()));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.<User>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(observer);
    }

    @Override
    public void sendVerificationCode(RxFragment fragment, Map<String, String> params, Observer<Object> observer) {
        Observable<HttpResult<Object>> observable = service.sendVerificationCode(params);

        observable.flatMap(new Function<HttpResult<Object>, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(HttpResult<Object> userHttpResult) throws Exception {

                if (userHttpResult.getCode() == 0) {
                    return Observable.just(new Object());
                }
                return Observable.error(new ServerException(userHttpResult.getCode(),userHttpResult.getMessage()));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.<Object>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(observer);

    }

    @Override
    public void uploadAvatar(RxFragment fragment, String filePath, String userId, Observer<Object> observer) {

        File file = new File(filePath);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("headImageFile", file.getName(), imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();


        Observable<HttpResult<Object>> observable = service.uploadAvatar(parts);


        observable.flatMap(new Function<HttpResult<Object>, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(HttpResult<Object> userHttpResult) throws Exception {

                if (userHttpResult.getCode() == 0) {
                    return Observable.just(new Object());
                }
                return Observable.error(new ServerException(userHttpResult.getCode(),userHttpResult.getMessage()));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.<Object>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(observer);


    }



    private <T> void buildObserver(Observable<T> observable,Observer<T> observer,LifecycleProvider lifecycleProvider){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose((lifecycleProvider instanceof RxAppCompatActivity) ?(((RxAppCompatActivity)lifecycleProvider).<T>bindUntilEvent(ActivityEvent.DESTROY)) :  (((RxFragment)lifecycleProvider).<T>bindUntilEvent(FragmentEvent.DETACH)))
                .subscribe(observer);
    }
    @Override
    public synchronized void release() {

        if(INSTANCE != null){
            INSTANCE = null;
        }
    }

    @Override
    public void saveUserTodB(User user) {

    }

    @Override
    public void getUserFromDb(LifecycleProvider lifecycleProvider, Observer<User> observer) {

    }


}
