package com.jy.fistga.data.source;

import com.jy.fistga.data.Channel;
import com.jy.fistga.data.ChannelData;
import com.jy.fistga.data.HttpResult;
import com.jy.fistga.data.NewsData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;

/*
 * created by taofu on 2018/9/5
 **/
public interface NewsDataSource {

    void loadChannels(LifecycleProvider lifecycleProvider, Observer<List<Channel>> observer);

    void loadNewsByChannel(LifecycleProvider lifecycleProvider, Map<String,String> params,Observer<NewsData> observer);
}
