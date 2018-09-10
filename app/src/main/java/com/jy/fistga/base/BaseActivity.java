package com.jy.fistga.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/*
 * created by taofu on 2018/8/29
 **/
public abstract  class BaseActivity extends RxAppCompatActivity {

    protected FragmentManager mFragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();

    }


    public <T extends  BaseFragment,E extends BasePresenter> void addFragment(@NonNull Class<T> tClass, E presenter,int containerId, String tag, Bundle args)  {

        if(TextUtils.isEmpty(tag)){
            tag = tClass.getName();
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment addedFragment = mFragmentManager.findFragmentByTag(tag);

        BaseFragment targetFragment = null;

        if(addedFragment == null){
            try {
                targetFragment = tClass.newInstance();
                targetFragment.setArguments(args);

                if(targetFragment instanceof BaseView){
                    //noinspection unchecked
                    ((BaseView) targetFragment).setPresenter(presenter);

                }
                addFragmentAnimation(fragmentTransaction,  targetFragment);
                fragmentTransaction.add(containerId,targetFragment, tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{

            targetFragment = (BaseFragment) addedFragment;
            addFragmentAnimation(fragmentTransaction,  targetFragment);
            targetFragment.setArguments(args);
            if(targetFragment.isHidden()){
                fragmentTransaction.show(targetFragment);
            }

        }

        if(targetFragment != null){
            hidePreFragment(fragmentTransaction,  targetFragment);

            if( targetFragment.isNeedAddToBackStack()){
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commit();
        }
    }



    private void addFragmentAnimation(FragmentTransaction transaction,BaseFragment baseFragment){

        transaction.setCustomAnimations(baseFragment.getSelfEnterAnimId(), baseFragment.getPreExistAnimId() ,baseFragment.getPrePopEnterAnimId(),baseFragment.getSelfPopExistAnimId());
    }

    private void hidePreFragment(FragmentTransaction transaction,BaseFragment baseFragment){
        if(baseFragment.isNeedHidePreFragment()){

            List<Fragment> addedFragments = mFragmentManager.getFragments();

            for(Fragment fragment: addedFragments){
                if(fragment != baseFragment){
                    transaction.hide(fragment);
                }
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
