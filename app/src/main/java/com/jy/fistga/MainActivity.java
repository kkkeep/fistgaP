package com.jy.fistga;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jy.fistga.base.BaseActivity;
import com.jy.fistga.base.GlideApp;
import com.jy.fistga.data.source.NewsDataRepository;
import com.jy.fistga.data.source.remote.NewsRemoteDataSource;
import com.jy.fistga.news.FragmentNews;
import com.jy.fistga.news.NewsPresenter;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NewsPresenter newsPresenter = new NewsPresenter(NewsDataRepository.getInstance(NewsRemoteDataSource.getInstance(), null));

        addFragment(FragmentNews.class, newsPresenter, R.id.main_fragment_container, null, null);

        ImageView imageView = findViewById(R.id.defaultImg);

       /* Glide.with(this).load("http://cdn3.nflximg.net/images/3093/2043093.jpg").listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);*/

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);



    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}





