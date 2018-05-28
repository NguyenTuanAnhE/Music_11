package com.framgia.anhnt.vmusic.screen.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.BaseActivity;
import com.framgia.anhnt.vmusic.data.model.Genre;
import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.screen.online.OnlineActivity;
import com.framgia.anhnt.vmusic.screen.player.PlayerActivity;
import com.framgia.anhnt.vmusic.utils.GenreType;
import com.framgia.anhnt.vmusic.utils.TabPosition;
import com.framgia.anhnt.vmusic.utils.TrackUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View, MainAdapter.OnGenreClickListener {

    private RecyclerView mRecyclerHome;
    private MainAdapter mMainAdapter;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean getFullScreen() {
        return true;
    }

    @Override
    protected boolean showHomeEnable() {
        return false;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.color_free_speech_red;
    }

    @Override
    public void initComponents() {
        mRecyclerHome = findViewById(R.id.recycler_main);

        mPresenter = new MainPresenter();
        mPresenter.setView(this);

        mMainAdapter = new MainAdapter(this);
        mRecyclerHome.setAdapter(mMainAdapter);
        mMainAdapter.setListener(this);
        mRecyclerHome.setLayoutManager(new LinearLayoutManager(this));
        getListGenre();
    }

    @Override
    public void showListGenre(List<Genre> genres) {
        mMainAdapter.updateGenres(genres);
    }

    public void getListGenre() {
        mPresenter.getListGenre();
    }

    @Override
    public void onItemClick(String genre) {
        if (genre.equals(GenreType.MY_MUSIC)) {
            //TODO: get local music
        } else {
            startActivity(OnlineActivity.getOnlineIntent(this, genre));
        }
    }

    @Override
    public void onPlayClick(int position) {
        startActivity(PlayerActivity.getPlayerIntent(this, position));
    }
}
