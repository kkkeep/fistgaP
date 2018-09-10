package com.jy.fistga.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jy.fistga.data.User;
import com.jy.fistga.data.source.remote.UserRemoteDataSource;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.trello.rxlifecycle2.internal.Preconditions;

import java.util.Map;

import io.reactivex.Observer;

/*
 * created by taofu on 2018/8/29
 **/
public class UserDataRepository implements UserDataSource {


    @NonNull
    private UserDataSource mRemoteDataSource;

    @NonNull
    private UserDataSource mLocalDataSource;

    @Nullable
    private static UserDataRepository INSTANCE = null;

    private UserDataRepository(@NonNull UserDataSource remoteDataSource,@NonNull UserDataSource localDataSource){
        mRemoteDataSource = Preconditions.checkNotNull(remoteDataSource, "Login remote data source is not allowed null");
        mLocalDataSource = Preconditions.checkNotNull(localDataSource, "Login local data source is not allowed null");

    }


    public static UserDataRepository getInstance(UserDataSource remoteDataSource,UserDataSource localDataSource){

        if(INSTANCE == null){
            synchronized (UserDataRepository.class){
                if(INSTANCE == null){
                    INSTANCE = new UserDataRepository(remoteDataSource,localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void login(RxFragment fragment, Map<String, String> params, Observer<User> observer) {

        mRemoteDataSource.login(fragment, params,observer);
    }

    @Override
    public void sendVerificationCode(RxFragment fragment, Map<String, String> params, Observer<Object> observer) {

        mRemoteDataSource.sendVerificationCode(fragment, params, observer);
    }



    @Override
    public void uploadAvatar(RxFragment fragment, String filePath, String userId, Observer<Object> objectObserver) {
        mRemoteDataSource.uploadAvatar(fragment, filePath, userId, objectObserver);
    }

    @Override
    public synchronized  void  release() {
        if(INSTANCE ==null){
            INSTANCE = null;
        }
    }

    @Override
    public void saveUserTodB(User user) {
        mLocalDataSource.saveUserTodB(user);
    }

    @Override
    public void getUserFromDb(LifecycleProvider lifecycleProvider, Observer<User> observer) {
        mLocalDataSource.getUserFromDb(lifecycleProvider, observer);
    }





   /* @Override
    public void getUserFromDb(LifecycleProvider fragment,Observer<User> observer) {
     mLocalDataSource.getUserFromDb(fragment,observer);

    }
*/

}
