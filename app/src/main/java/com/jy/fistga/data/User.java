package com.jy.fistga.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/*
 * created by taofu on 2018/8/29
 **/
@Entity
public class User {


    
    private int comments;
    private int favorites;
    private int following;
    private int historyReads;
    private int isWifiImages;
    private String nickname;
    private String personalProfile;
    private String headImagePath;
    private String phone;
    private int unMessages;
    @Id
    private String userId;


    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getUnMessages() {
        return this.unMessages;
    }
    public void setUnMessages(int unMessages) {
        this.unMessages = unMessages;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getHeadImagePath() {
        return this.headImagePath;
    }
    public void setHeadImagePath(String headImagePath) {
        this.headImagePath = headImagePath;
    }
    public String getPersonalProfile() {
        return this.personalProfile;
    }
    public void setPersonalProfile(String personalProfile) {
        this.personalProfile = personalProfile;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public int getIsWifiImages() {
        return this.isWifiImages;
    }
    public void setIsWifiImages(int isWifiImages) {
        this.isWifiImages = isWifiImages;
    }
    public int getHistoryReads() {
        return this.historyReads;
    }
    public void setHistoryReads(int historyReads) {
        this.historyReads = historyReads;
    }
    public int getFollowing() {
        return this.following;
    }
    public void setFollowing(int following) {
        this.following = following;
    }
    public int getFavorites() {
        return this.favorites;
    }
    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }
    public int getComments() {
        return this.comments;
    }
    public void setComments(int comments) {
        this.comments = comments;
    }
    @Generated(hash = 1475732689)
    public User(int comments, int favorites, int following, int historyReads,
            int isWifiImages, String nickname, String personalProfile,
            String headImagePath, String phone, int unMessages, String userId) {
        this.comments = comments;
        this.favorites = favorites;
        this.following = following;
        this.historyReads = historyReads;
        this.isWifiImages = isWifiImages;
        this.nickname = nickname;
        this.personalProfile = personalProfile;
        this.headImagePath = headImagePath;
        this.phone = phone;
        this.unMessages = unMessages;
        this.userId = userId;
    }
    @Generated(hash = 586692638)
    public User() {
    }


    



}
