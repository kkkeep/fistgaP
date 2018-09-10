package com.jy.fistga.data;

/*
 * created by taofu on 2018/9/5
 **/

import com.jy.fistga.data.NewsData;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public  class News {
    @Id
    private String newsId;
    private int pageviews;

    @Convert(columnType = String.class ,converter = NewsData.NewsImageConvert.class)
    private List<String> imageListThumb;

    private int isTop;
    private int layoutType;
    private String origin;
    private String publishTime;
    private String title;






    public String getNewsId() {
        return this.newsId;
    }




    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }




    public List<String> getImageListThumb() {
        return this.imageListThumb;
    }




    public void setImageListThumb(List<String> imageListThumb) {
        this.imageListThumb = imageListThumb;
    }




    public int getPageviews() {
        return this.pageviews;
    }




    public void setPageviews(int pageviews) {
        this.pageviews = pageviews;
    }




    public String getTitle() {
        return this.title;
    }




    public void setTitle(String title) {
        this.title = title;
    }




    public String getPublishTime() {
        return this.publishTime;
    }




    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }




    public String getOrigin() {
        return this.origin;
    }




    public void setOrigin(String origin) {
        this.origin = origin;
    }




    public int getLayoutType() {
        return this.layoutType;
    }




    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }




    public int getIsTop() {
        return this.isTop;
    }




    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }




    @Generated(hash = 662088346)
    public News(String newsId, int pageviews, List<String> imageListThumb,
            int isTop, int layoutType, String origin, String publishTime,
            String title) {
        this.newsId = newsId;
        this.pageviews = pageviews;
        this.imageListThumb = imageListThumb;
        this.isTop = isTop;
        this.layoutType = layoutType;
        this.origin = origin;
        this.publishTime = publishTime;
        this.title = title;
    }




    @Generated(hash = 1579685679)
    public News() {
    }





}
