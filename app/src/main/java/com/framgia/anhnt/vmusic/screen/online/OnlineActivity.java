package com.framgia.anhnt.vmusic.screen.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.framgia.anhnt.vmusic.BaseActivity;
import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.utils.GenreType;
import com.framgia.anhnt.vmusic.utils.TabPosition;

public class OnlineActivity extends BaseActivity implements OnlineContract.View, View.OnClickListener {
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
        return R.color.color_free_speech_red;
    }

    @Override
    public void initComponents() {
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.image_search_background).setOnClickListener(this);
        mTabGenre = findViewById(R.id.tab_online);
        mPagerGenre = findViewById(R.id.view_pager_genre);
        mPagerAdapter = new OnlinePagerAdapter(getSupportFragmentManager());
        mPagerGenre.setAdapter(mPagerAdapter);
        mTabGenre.setupWithViewPager(mPagerGenre);

        setSelectedTab();

    }

    private void setSelectedTab() {
        Intent intent = getIntent();
        if (intent != null) {
            int genre = intent.getIntExtra(GenreType.ARGUMENT_GENRE, TabPosition.TAB_ALL_MUSIC);
            mPagerGenre.setCurrentItem(genre);
            Log.d("TAG", "initComponents: " + genre);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                onBackPressed();
                break;
            case R.id.image_search_background:
                break;
            default:
                break;
        }
    }
}
