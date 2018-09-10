package com.jy.fistga.news;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jy.fistga.R;
import com.jy.fistga.base.BaseFragment;
import com.jy.fistga.data.Channel;
import com.jy.fistga.utils.StatusBarManager;

import java.util.List;

/*
 * created by taofu on 2018/9/5
 **/
public class FragmentNews extends BaseFragment implements NewsContract.NewsView {

    private NewsContract.NewsPresenter mNewsPresenter;
    private Toolbar mToolbar;
    private ViewPager mNewsPager;
    private TabLayout mTabLayout;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
        StatusBarManager.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.text_bg_color_g));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mNewsPresenter != null) {
            mNewsPresenter.getChannels();
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mToolbar = view.findViewById(R.id.main_news_toolbar);
        mNewsPager = view.findViewById(R.id.main_news_viewpager);
        mTabLayout = view.findViewById(R.id.main_news_tab);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_book_cover, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {

        }
        return true;
    }


    @Override
    public void setPresenter(NewsContract.NewsPresenter newsPresenter) {
        mNewsPresenter = newsPresenter;
        mNewsPresenter.attachView(this);
    }

    @Override
    public void onChannelsSuccess(List<Channel> channels) {


        NewsPageAdapter newsPageAdapter = new NewsPageAdapter(getChildFragmentManager(), channels);
        mNewsPager.setAdapter(newsPageAdapter);
        mTabLayout.setupWithViewPager(mNewsPager);
    }

    @Override
    public void onChannelsFail(String msg) {
        Toast.makeText(getContext(), "加载频道列表失败", Toast.LENGTH_SHORT).show();
    }
}

class NewsPageAdapter extends FragmentStatePagerAdapter {

    private List<Channel> mChannelList;

    public NewsPageAdapter(FragmentManager fm, List<Channel> channelList) {
        super(fm);
        mChannelList = channelList;
    }

    @Override
    public Fragment getItem(int position) {
        FragmentChannelNewsPage fragmentChannelNewsPage = new FragmentChannelNewsPage();
        Bundle bundle = new Bundle();
        bundle.putString("channelId", mChannelList.get(position).getChannelId());
        bundle.putString("channelName", mChannelList.get(position).getChannelName());
        fragmentChannelNewsPage.setArguments(bundle);

        return fragmentChannelNewsPage;
    }

    @Override
    public int getCount() {
        return mChannelList.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mChannelList.get(position).getChannelName();
    }
}
