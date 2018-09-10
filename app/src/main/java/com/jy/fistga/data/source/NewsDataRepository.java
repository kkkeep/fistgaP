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
public class NewsDataRepository implements NewsDataSource {

    private static NewsDataRepository INSTANCE;

    private NewsDataSource mRemote;
    private NewsDataSource mLocal;

    private NewsDataRepository(NewsDataSource remote,NewsDataSource local){

        mRemote = remote;
        mLocal = local;


    }


    public static synchronized NewsDataRepository getInstance(NewsDataSource remote,NewsDataSource local) {
        if (INSTANCE == null) {
            INSTANCE = new NewsDataRepository(remote, local);
        }

        return INSTANCE;
    }

    @Override
    public void loadChannels(LifecycleProvider lifecycleProvider, Observer<List<Channel>> observer) {
        mRemote.loadChannels(lifecycleProvider, observer);
    }

    @Override
    public void loadNewsByChannel(LifecycleProvider lifecycleProvider, Map<String, String> params, Observer<NewsData> observer) {
        mRemote.loadNewsByChannel(lifecycleProvider, params, observer);
    }
}
