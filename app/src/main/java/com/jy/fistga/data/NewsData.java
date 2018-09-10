package com.jy.fistga.data;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.annotation.Annotation;
import java.util.List;

/*
 * created by taofu on 2018/9/5
 **/
public class NewsData {

    private String maxCursor;
    private String minCursor;

    private int tops;

    private List<News> newList;


    public String getMaxCursor() {
        return maxCursor;
    }

    public void setMaxCursor(String maxCursor) {
        this.maxCursor = maxCursor;
    }

    public String getMinCursor() {
        return minCursor;
    }

    public void setMinCursor(String minCursor) {
        this.minCursor = minCursor;
    }

    public int getTops() {
        return tops;
    }

    public void setTops(int tops) {
        this.tops = tops;
    }

    public List<News> getNewList() {
        return newList;
    }

    public void setNewList(List<News> newList) {
        this.newList = newList;
    }

    public static class NewsImageConvert implements PropertyConverter<List<String>,String> {


        @Override
        public List<String> convertToEntityProperty(String databaseValue) {
            return null;
        }

        @Override
        public String convertToDatabaseValue(List<String> entityProperty) {
            return null;
        }
    }

}



