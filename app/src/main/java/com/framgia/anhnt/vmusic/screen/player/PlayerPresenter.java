package com.framgia.anhnt.vmusic.screen.player;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.repositories.TrackRepository;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;

import java.util.List;

public class PlayerPresenter implements PlayerContract.Presenter,
        TrackDataSource.OnFetchDataListener<List<Track>> {
    private PlayerContract.View mView;
    private TrackRepository mTrackRepository;

    public PlayerPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void setView(PlayerContract.View view) {
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

    }

    @Override
    public void onFetchDataFail(String message) {

    }
}
