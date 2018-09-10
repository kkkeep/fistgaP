package com.jy.fistga.news;

import com.jy.fistga.base.BaseFragment;
import com.jy.fistga.data.Channel;
import com.jy.fistga.data.source.NewsDataSource;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/*
 * created by taofu on 2018/9/6
 **/
public class NewsPresenter implements NewsContract.NewsPresenter {

    private NewsContract.NewsView mNewsView;

    private NewsDataSource mDataRepository;

    public NewsPresenter(NewsDataSource dataRepository) {
        this.mDataRepository = dataRepository;
    }

    @Override
    public void getChannels() {
        mDataRepository.loadChannels((BaseFragment) mNewsView, new Observer<List<Channel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Channel> channels) {
                mNewsView.onChannelsSuccess(channels);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void attachView(NewsContract.NewsView baseNewsView) {
        mNewsView = baseNewsView;
    }

    @Override
    public void detachView() {

    }
}
