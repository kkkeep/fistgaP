package com.jy.fistga.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.fistga.utils.Logger;
import com.trello.rxlifecycle2.components.support.RxFragment;

/*
 * created by taofu on 2018/8/29
 **/
public  class BaseFragment extends RxFragment {

    protected BaseActivity mActivity;
    private  String TAG ;

    protected String getTagName(){

        return getClass().getSimpleName();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = getTagName();
        mActivity = (BaseActivity) context;
    }

    protected boolean isNeedAddToBackStack() {
        return true;
    }

    protected boolean isNeedHidePreFragment() {
        return true;
    }


    /**
     * 获取自己进入的动画
     */
    protected int getSelfEnterAnimId() {
        return 0;
    }

    /**
     * 获取上一个fragment 出去的动画
     */
    protected int getPreExistAnimId() {
        return 0;
    }

    /**
     * 获取上一个进入的动画（当按返回键时，自己被弹出，上一个再次进入时）
     */

    protected int getPrePopEnterAnimId() {
        return 0;
    }

    /**
     * 获取自己在按返回家被弹出时的动画
     */
    protected int getSelfPopExistAnimId() {
        return 0;
    }



    protected void updateArguments(Bundle bundle){
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("%s onCreate ",TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d("%s onStart ",TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("%s onResume ",TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("%s onPause ",TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d("%s onStop ",TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d("%s onDestroyView ",TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("%s onDestroy ",TAG);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d("%s onDetach ",TAG);
    }
}
