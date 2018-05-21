package com.framgia.anhnt.vmusic.screen.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.framgia.anhnt.vmusic.BaseActivity;
import com.framgia.anhnt.vmusic.R;

public class OnlineActivity extends BaseActivity {
    private TabLayout mTabGenre;
    private ViewPager mPagerGenre;
    private OnlinePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean showHomeEnable() {
        return true;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_online;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.color_online_status_bar;
    }

    @Override
    public void initComponents() {
        mTabGenre = findViewById(R.id.tabs);
        mPagerGenre = findViewById(R.id.view_pager_genre);
        mPagerAdapter = new OnlinePagerAdapter(getSupportFragmentManager());
        mPagerGenre.setAdapter(mPagerAdapter);
        mTabGenre.setupWithViewPager(mPagerGenre);
        mPagerGenre.setCurrentItem(0);
    }
}
