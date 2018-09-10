package com.jy.fistga.data;

/*
 * created by taofu on 2018/9/5
 **/

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Channel {
    @Id
    private String channelId;
    private String channelName;
    public String getChannelName() {
        return this.channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public String getChannelId() {
        return this.channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    @Generated(hash = 1363842761)
    public Channel(String channelId, String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
    }
    @Generated(hash = 459652974)
    public Channel() {
    }


}
