package com.jy.fistga.news;

import com.jy.fistga.base.MyObserver;
import com.jy.fistga.data.NewsData;
import com.jy.fistga.data.source.NewsDataRepository;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.HashMap;
import java.util.Map;

/*
 * created by taofu on 2018/9/6
 **/
public class ChannelNewsPagePresenter implements NewsContract.ChannelNewPagePresenter {


    private NewsDataRepository mNewsDataRepository;

    private NewsContract.ChannelNewsPageView mView;

    public ChannelNewsPagePresenter(NewsDataRepository mNewsDataRepository) {
        this.mNewsDataRepository = mNewsDataRepository;
    }


    @Override
    public void getNewsByChannel(String channelId) {

        Map<String,String> parmas = new HashMap<>();

        parmas.put("channelId", channelId);
        parmas.put("cursor", "0");


        mNewsDataRepository.loadNewsByChannel((RxFragment)mView, parmas, new MyObserver<NewsData>(){
            @Override
            public void onNext(NewsData newsData) {
                mView.onNewsListSuccess(newsData);
            }

            @Override
            public void onError(Throwable e) {
                mView.onNewsListFail(e.getMessage());
            }
        });
    }

    @Override
    public void attachView(NewsContract.ChannelNewsPageView baseView) {
        mView = baseView;
    }

    @Override
    public void detachView() {

    }
}
