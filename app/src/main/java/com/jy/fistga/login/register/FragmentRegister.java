package com.jy.fistga.login.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.module.AppGlideModule;
import com.jy.fistga.R;
import com.jy.fistga.base.BaseFragment;
import com.jy.fistga.base.GlideApp;
import com.jy.fistga.utils.Logger;
import com.jy.fistga.utils.StatusBarManager;
import com.jy.fistga.view.EditImageView;

/*
 * created by taofu on 2018/8/31
 **/
public class FragmentRegister extends BaseFragment implements RegisterContact.View {

    private static final String TAG = "FragmentRegister";


    private RegisterContact.Presenter mPresenter;
    private Uri mPhotoUri;

    private String filePath;
    private Group mGroupEditPhoto;
    private Group mGroupRegister;

    private FrameLayout mEditImageViewLayout;
    private ImageView mIvUserHeadPic;
    private Button mEditOk;
    private Button mEditCancle;
    private EditImageView mEditImageView;
    private ImageView mBtnClose;
    private Button mBtnDone;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater.inflate(R.layout.fragment_register, container, false));

    }


    private View initView(View view) {
        StatusBarManager.lightStatusBar(getActivity());
        mIvUserHeadPic = view.findViewById(R.id.login_register_iv_pic);

        mIvUserHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.takePhotoFromCamera();
            }
        });


        mGroupEditPhoto = view.findViewById(R.id.group);
        mGroupRegister = view.findViewById(R.id.group2);


        mEditImageViewLayout = view.findViewById(R.id.login_register_edite_pic);
        mGroupRegister.setVisibility(View.VISIBLE);
        mGroupEditPhoto.setVisibility(View.GONE);

        mEditOk = view.findViewById(R.id.login_register_btn_edite_pic_ok);
        mEditCancle = view.findViewById(R.id.login_register_btn_edite_pic_cancle);

        mEditOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveBitmap(mEditImageView.getCircleBitmap());

            }
        });

        mEditCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupEditPhoto.setVisibility(View.INVISIBLE);
                mGroupRegister.setVisibility(View.VISIBLE);
                mGroupEditPhoto.requestLayout();
            }
        });


        mBtnClose = view.findViewById(R.id.login_register_iv_close);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnDone = view.findViewById(R.id.login_register_btn_done);

        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.uploadAvatar();
            }
        });

        return view;
    }


    @Override
    protected int getPreExistAnimId() {
        return R.anim.common_page_left_out;
    }


    @Override
    protected int getSelfPopExistAnimId() {
        return R.anim.common_page_right_out;
    }


    @Override
    protected int getSelfEnterAnimId() {
        return R.anim.common_page_right_in;
    }

    @Override
    protected int getPrePopEnterAnimId() {
        return R.anim.common_page_left_in;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onSaveBitmapSuccess(String filePath) {
        mGroupEditPhoto.setVisibility(View.INVISIBLE);
        mGroupRegister.setVisibility(View.VISIBLE);
        GlideApp.with(this).load(filePath).into(mIvUserHeadPic);
    }

    @Override
    public void onSaveBitmapFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadAvatarSuccess() {
    }

    @Override
    public void uploadAvatarFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onTakePhotoFail(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTakePhotoSuccess(String filePath) {
        mGroupEditPhoto.setVisibility(View.VISIBLE);
        mGroupRegister.setVisibility(View.INVISIBLE);
        mEditImageView = new EditImageView(getActivity(), filePath);
        mEditImageViewLayout.addView(mEditImageView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }


    @Override
    public void setPresenter(RegisterContact.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);

    }




}
