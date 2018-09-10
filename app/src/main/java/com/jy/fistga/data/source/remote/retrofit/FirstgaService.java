package com.jy.fistga.data.source.remote.retrofit;

import com.jy.fistga.data.ChannelData;
import com.jy.fistga.data.HttpResult;
import com.jy.fistga.data.NewsData;
import com.jy.fistga.data.User;

import java.util.List;
import java.util.Map;
import java.util.Observer;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/*
 * created by taofu on 2018/8/29
 **/
public interface FirstgaService {

    @POST("users/phoneLogin")
    Observable<HttpResult<User>> login(@Body Map<String, String> params);


    @POST("users/sendVerificationCode")
    Observable<HttpResult<Object>> sendVerificationCode(@Body Map<String, String> params);


    @POST("users/uploadHeadImage")
    @Multipart
    Observable<HttpResult<Object>> uploadAvatar(@Part List<MultipartBody.Part> partList);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("news/listNewsChannel")
    Observable<HttpResult<ChannelData>> loadChannels();

    @POST("news/upListNews")
    Observable<HttpResult<NewsData>> fetchNewNews(@Body Map<String, String> params);

}
