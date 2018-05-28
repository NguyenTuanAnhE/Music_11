package com.framgia.anhnt.vmusic.screen.online;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.framgia.anhnt.vmusic.BaseActivity;
import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.service.MediaService;
import com.framgia.anhnt.vmusic.utils.TabPosition;
import com.framgia.anhnt.vmusic.utils.TrackUtils;

public class OnlineActivity extends BaseActivity implements OnlineContract.View, View.OnClickListener {
    private static final String ARGUMENT_GENRE = "genre";
    private TabLayout mTabGenre;
    private ViewPager mPagerGenre;
    private OnlinePagerAdapter mPagerAdapter;
    private MediaService mMediaService;
    private boolean mIsBound;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            mMediaService = binder.getService();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mIsBound = false;
        }
    };

    public static Intent getOnlineIntent(Context context, String genre) {
        Intent intent = new Intent(context, OnlineActivity.class);
        intent.putExtra(ARGUMENT_GENRE, genre);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MediaService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mIsBound = false;
    }

    @Override
    protected boolean getFullScreen() {
        return false;
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
        mPagerGenre.setOffscreenPageLimit(TabPosition.TAB_COUNT);
        setSelectedTab();
    }

    private void setSelectedTab() {
        Intent intent = getIntent();
        if (intent != null) {
            String genre = intent.getStringExtra(ARGUMENT_GENRE);
            mPagerGenre.setCurrentItem(TrackUtils.getTabPosition(genre));
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
