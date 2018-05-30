package com.framgia.anhnt.vmusic.screen.search;

import android.util.Log;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.repositories.TrackRepository;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;
import com.framgia.anhnt.vmusic.screen.online.genre.GenreContract;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter,
        TrackDataSource.OnFetchDataListener<List<Track>> {
    private SearchContract.View mView;
    private TrackRepository mTrackRepository;

    public SearchPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;

    }

    @Override
    public void searchTrack(int limit, String key) {
        mTrackRepository.searchTrack(limit, key, this);
    }


    @Override
    public void setView(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onFetchDataSuccess(List<Track> data) {
        mView.showListTrack(data);
    }

    @Override
    public void onFetchDataFail(String message) {
        mView.showFailMessage();
    }
}
