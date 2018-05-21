package com.framgia.anhnt.vmusic.screen.home;

import android.util.Log;

import com.framgia.anhnt.vmusic.data.model.Genre;
import com.framgia.anhnt.vmusic.data.repository.GenreRepository;
import com.framgia.anhnt.vmusic.data.source.GenreDataSource;
import com.framgia.anhnt.vmusic.data.source.local.GenreLocalDataSource;
import com.framgia.anhnt.vmusic.data.source.remote.GenreRemoteDataSource;

import java.util.List;

public class MainPresenter implements MainContract.Presenter,
        GenreDataSource.OnFetchDataListener {

    private MainContract.View mView;
    private GenreRepository mGenreRepository;

    public MainPresenter() {
        GenreDataSource.LocalDataSource local = GenreLocalDataSource.getInstance();
        GenreDataSource.RemoteDataSource remote = GenreRemoteDataSource.getInstance();
        mGenreRepository = GenreRepository.getInstance(local, remote);
    }

    @Override
    public void setView(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void getListGenre() {
        mGenreRepository.getLocalGenres(this);
    }

    @Override
    public void onFetchDataSuccess(List data) {
        mView.showListGenre(data);
    }

    @Override
    public void onFetchDataFail(String message) {
    }


}
