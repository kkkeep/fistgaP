package com.jy.fistga.data.source.remote;

import com.jy.fistga.data.Channel;
import com.jy.fistga.data.ChannelData;
import com.jy.fistga.data.HttpResult;
import com.jy.fistga.data.NewsData;
import com.jy.fistga.data.source.NewsDataSource;
import com.jy.fistga.data.source.remote.retrofit.DataRetrofit;
import com.jy.fistga.data.source.remote.retrofit.FirstgaService;
import com.jy.fistga.exception.ServerException;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by taofu on 2018/9/5
 **/
public class NewsRemoteDataSource implements NewsDataSource {


    private FirstgaService service;

    private static NewsRemoteDataSource INSTANCE;

    private NewsRemoteDataSource() {
        service = DataRetrofit.getRetrofitService();

    }


    public static synchronized NewsRemoteDataSource  getInstance(){

        if(INSTANCE == null){
            INSTANCE = new NewsRemoteDataSource();
        }
        return INSTANCE;
    }
    @Override
    public void loadChannels(LifecycleProvider lifecycleProvider, Observer<List<Channel>> observer) {

        Observable<HttpResult<ChannelData>> observable = service.loadChannels();

       buildObserver(observable.flatMap(new Function<HttpResult<ChannelData>, ObservableSource<List<Channel>>>() {
           @Override
           public ObservableSource<List<Channel>> apply(HttpResult<ChannelData> channelDataHttpResult) throws Exception {
               if(channelDataHttpResult.getCode() == 0 ){
                   if( channelDataHttpResult.getData() != null && channelDataHttpResult.getData().getNewsChannelList() != null){
                       return Observable.just(channelDataHttpResult.getData().getNewsChannelList());
                   }else{
                       return Observable.error(new ServerException(1001,"服务器内部异常"));
                   }

               }
               return Observable.error(new ServerException(channelDataHttpResult.getCode(),channelDataHttpResult.getMessage()));
           }
       }), observer,lifecycleProvider );


    }

    @Override
    public void loadNewsByChannel(LifecycleProvider lifecycleProvider, Map<String, String> params, Observer<NewsData> observer) {
        Observable<HttpResult<NewsData>> observable = service.fetchNewNews(params);
        buildObserver(observable.flatMap(new Function<HttpResult<NewsData>, ObservableSource<NewsData>>() {
            @Override
            public ObservableSource<NewsData> apply(HttpResult<NewsData> newsDataHttpResult) throws Exception {
                if(newsDataHttpResult.getCode() == 0){
                    NewsData data = newsDataHttpResult.getData();
                    if(data != null && data.getNewList()!= null && data.getNewList().size() > 0){
                        return Observable.just(data);
                    }else{
                        return Observable.error(new ServerException(1001,"服务器内部异常"));
                    }

                }
                return Observable.error(new ServerException(newsDataHttpResult.getCode(),newsDataHttpResult.getMessage()));
            }
        }), observer, lifecycleProvider);
    }

    private <T> void buildObserver(Observable<T> observable,Observer<T> observer,LifecycleProvider lifecycleProvider){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose((lifecycleProvider instanceof RxAppCompatActivity) ?(((RxAppCompatActivity)lifecycleProvider).<T>bindUntilEvent(ActivityEvent.DESTROY)) :  (((RxFragment)lifecycleProvider).<T>bindUntilEvent(FragmentEvent.DETACH)))
                .subscribe(observer);
    }
}
