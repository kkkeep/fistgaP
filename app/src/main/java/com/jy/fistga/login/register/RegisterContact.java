package com.jy.fistga.login.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.jy.fistga.base.BasePresenter;
import com.jy.fistga.base.BaseView;

/*
 * created by taofu on 2018/9/2
 **/
public interface RegisterContact {



    public interface View extends BaseView<RegisterContact.Presenter>{

        void onTakePhotoSuccess(String filePath); // 拍照后大图的 filepath

        void onTakePhotoFail(String msg); // 拍照保存图片到指定路径失败

        void onSaveBitmapSuccess(String filePath); // 保存剪切后的原图 filePath

        void onSaveBitmapFail(String msg); // 保存剪切图片失败


        void uploadAvatarSuccess();
        void uploadAvatarFail(String msg);


    }


    public interface Presenter extends BasePresenter<RegisterContact.View>{

        void takePhotoFromCamera(); // 通过相机拍照
        void getPhotoFromGallery(); // 通过相册获取照片

        void saveBitmap(Bitmap bitmap); // 保存剪切后的圆形bitmap

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
        void onActivityResult(int requestCode, int resultCode, Intent data);


        void uploadAvatar();



    }
}
