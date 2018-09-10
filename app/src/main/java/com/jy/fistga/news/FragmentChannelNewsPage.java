package com.jy.fistga.news;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.fistga.R;
import com.jy.fistga.base.BaseFragment;
import com.jy.fistga.base.GlideApp;
import com.jy.fistga.base.GlideRequest;
import com.jy.fistga.data.News;
import com.jy.fistga.data.NewsData;
import com.jy.fistga.data.source.NewsDataRepository;
import com.jy.fistga.data.source.remote.NewsRemoteDataSource;
import com.jy.fistga.utils.Logger;
import com.jy.fistga.view.NewItemBottomInfoView;

import java.util.ArrayList;
import java.util.List;

/*
 * created by taofu on 2018/9/6
 **/
public class FragmentChannelNewsPage extends BaseFragment implements NewsContract.ChannelNewsPageView{

    private static final String TAG = "FragmentChannelNewsPage";

    private NewsContract.ChannelNewPagePresenter mPresenter;
    private String mChannelId;
    private String mChannelName;

    private RecyclerView mRecyclerView;

    private int position = 0;
    private int scorllY = 0;

    @Override
    protected String getTagName() {
        return super.getTagName() +  "_" + mChannelName;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if(args != null){
            mChannelId = args.getString("channelId");
            mChannelName = args.getString("channelName");
        }

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            position = savedInstanceState.getInt("position",0);
            scorllY = savedInstanceState.getInt("scrollY",0);
        }
        setPresenter(new ChannelNewsPagePresenter(NewsDataRepository.getInstance(NewsRemoteDataSource.getInstance(),null)));
        mPresenter.getNewsByChannel(mChannelId);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_news_page, container, false);
        mRecyclerView = view.findViewById(R.id.main_channel_news_page_recyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        return view;
    }






    private void getItemPositionAndScrollY(){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int fistVP = linearLayoutManager.findFirstVisibleItemPosition();
        View v = linearLayoutManager.findViewByPosition(fistVP);
        position = fistVP;
        scorllY = v.getTop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        getItemPositionAndScrollY();
        outState.putInt("position", position);
        outState.putInt("scrollY", scorllY);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();



    }

    @Override
    public void onNewsListSuccess(NewsData news) {

        for(News newss : news.getNewList()){

            if(newss.getLayoutType() == 1){
                ArrayList arrayList = new ArrayList();
                arrayList.add(newss.getImageListThumb().get(0));
                arrayList.add(newss.getImageListThumb().get(0));
                arrayList.add(newss.getImageListThumb().get(0));
                newss.setLayoutType(3);
                newss.setImageListThumb(arrayList);
                break;
            }
        }

        ChannelNewsPageAdapter adapter = new ChannelNewsPageAdapter(news.getNewList());

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        scrollToTargetPosition();


    }

    private void scrollToTargetPosition(){
        if(position != 0 && scorllY != 0){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            linearLayoutManager.scrollToPositionWithOffset(position, scorllY);
        }


    }

    @Override
    public void onNewsListFail(String msg) {

    }

    @Override
    public void setPresenter(NewsContract.ChannelNewPagePresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }


}


class ChannelNewsPageAdapter extends RecyclerView.Adapter<ChannelNewsPageAdapter.BaseNewsItemHolder> {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_RIGHT_IMG = 1;
    private static final int TYPE_BIG_IMG = 2;
    private static final int TYPE_THREE_IMG = 3;
    private GlideRequest<Drawable> mImageRequest;
    private List<News> mData;

    public ChannelNewsPageAdapter(List<News> news) {
        mData = news;
    }

    @NonNull
    @Override
    public BaseNewsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(mImageRequest == null){
            mImageRequest = GlideApp.with(parent).asDrawable();
        }


        switch (viewType){
            case TYPE_TITLE:{
                return new TitleViewHolderNews(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item_only_title, parent, false));
            }
            case TYPE_RIGHT_IMG:{
                return new RightImageViewHolderNews(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item_right_image, parent, false));

            }
            case TYPE_THREE_IMG:{
                return new ThreeImageViewHolderNews(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item_three_image, parent, false));

            }
            case TYPE_BIG_IMG:{
                return new BigImageViewHolderNews(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item_big_image, parent, false));

            }
            default:{
                return new TitleViewHolderNews(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item_only_title, parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseNewsItemHolder holder, int position) {
        News news = mData.get(position);

        holder.title.setText(news.getTitle());
        holder.infoView.setNews(news);


        if(holder instanceof RightImageViewHolderNews){
            RightImageViewHolderNews holderNews = (RightImageViewHolderNews) holder;
            mImageRequest.placeholder(R.drawable.news_item_right_image_default).load(news.getImageListThumb().get(0)).into(holderNews.ivRight);
        }else if(holder instanceof ThreeImageViewHolderNews){
            ThreeImageViewHolderNews holderNews = (ThreeImageViewHolderNews) holder;
            mImageRequest.placeholder(R.drawable.news_item_three_image_default).load(news.getImageListThumb().get(0)).into(holderNews.iv1);
            mImageRequest.placeholder(R.drawable.news_item_three_image_default).load(news.getImageListThumb().get(1)).into(holderNews.iv2);
            mImageRequest.placeholder(R.drawable.news_item_three_image_default).load(news.getImageListThumb().get(2)).into(holderNews.iv3);
        }else if(holder instanceof BigImageViewHolderNews){
            BigImageViewHolderNews holderNews = (BigImageViewHolderNews) holder;
            mImageRequest.placeholder(R.drawable.news_itme_big_image_default).load(news.getImageListThumb().get(0)).into(holderNews.ivBig);

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public int getItemViewType(int position) {

        switch (mData.get(position).getLayoutType()){
            case 0:
                return TYPE_TITLE;
            case 1:
                return TYPE_RIGHT_IMG;
            case 2:
                return TYPE_BIG_IMG;
            case 3:
                return TYPE_THREE_IMG;
        }

        return TYPE_TITLE;

    }




    class BaseNewsItemHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private NewItemBottomInfoView infoView;

         BaseNewsItemHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.news_item_title);
             infoView = itemView.findViewById(R.id.news_item_bottom_info);
        }

    }

    class BigImageViewHolderNews extends BaseNewsItemHolder {

        private ImageView ivBig;
        public BigImageViewHolderNews(View itemView) {
            super(itemView);
            ivBig = itemView.findViewById(R.id.news_item_big_img);
        }


    }

    class TitleViewHolderNews extends BaseNewsItemHolder {

        public TitleViewHolderNews(View itemView) {
            super(itemView);
        }
    }

    class ThreeImageViewHolderNews extends BaseNewsItemHolder {
        private ImageView iv1;
        private ImageView iv2;
        private ImageView iv3;

        public ThreeImageViewHolderNews(View itemView) {
            super(itemView);
            iv1 = itemView.findViewById(R.id.news_item_fist_img);
            iv2 = itemView.findViewById(R.id.news_item_second_img);
            iv3 = itemView.findViewById(R.id.news_item_third_img);
        }
    }

    class RightImageViewHolderNews extends BaseNewsItemHolder {
        private ImageView ivRight;
        public RightImageViewHolderNews(View itemView) {
            super(itemView);
            ivRight = itemView.findViewById(R.id.news_item_right_img);
        }
    }
}
