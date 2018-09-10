package com.jy.fistga.news;

import com.jy.fistga.base.BasePresenter;
import com.jy.fistga.base.BaseView;
import com.jy.fistga.data.Channel;
import com.jy.fistga.data.News;
import com.jy.fistga.data.NewsData;

import java.util.List;

/*
 * created by taofu on 2018/9/6
 **/
public interface NewsContract {


    public interface NewsView extends BaseView<NewsPresenter>{

        void onChannelsSuccess(List<Channel> channels);
        void onChannelsFail(String msg);

    }


    public interface NewsPresenter extends BasePresenter<NewsView>{
        void getChannels();
    }

    public interface ChannelNewsPageView extends  BaseView<ChannelNewPagePresenter>{

        void onNewsListSuccess(NewsData news);
        void onNewsListFail(String msg);
    }

    public interface ChannelNewPagePresenter extends BasePresenter<ChannelNewsPageView>{

        void getNewsByChannel(String channelId);
    }
}
