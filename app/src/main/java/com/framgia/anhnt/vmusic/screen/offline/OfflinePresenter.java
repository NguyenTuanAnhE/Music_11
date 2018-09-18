package com.framgia.anhnt.vmusic.screen.offline;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.repositories.TrackRepository;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;
import com.framgia.anhnt.vmusic.screen.player.PlayerContract;

import java.util.List;

public class OfflinePresenter implements OfflineContract.Presenter,
        TrackDataSource.OnFetchDataListener<List<Track>> {
    private OfflineContract.View mView;
    private TrackRepository mTrackRepository;

    public OfflinePresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void getLocalTracks() {
        mTrackRepository.getLocalTrack(this);
    }

    @Override
    public void setView(OfflineContract.View view) {
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
        mView.showListTracks(data);
    }

    @Override
    public void onFetchDataFail(String message) {

    }
}
