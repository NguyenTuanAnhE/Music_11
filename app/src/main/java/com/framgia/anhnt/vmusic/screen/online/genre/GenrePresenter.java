package com.framgia.anhnt.vmusic.screen.online.genre;

import android.util.Log;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.repositories.TrackRepository;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;
import com.framgia.anhnt.vmusic.data.source.local.TrackLocalDataSource;
import com.framgia.anhnt.vmusic.data.source.remote.TrackRemoteDataSource;

import java.util.List;

public class GenrePresenter implements GenreContract.Presenter,
        TrackDataSource.OnFetchDataListener<List<Track>> {
    private GenreContract.View mView;
    private TrackRepository mTrackRepository;

    public GenrePresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;

    }

    @Override
    public void setView(GenreContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadTrackByGenre(String genre, int limit, int offset) {
        mTrackRepository.getRemoteTrack(genre, limit, offset, this);
    }


    @Override
    public void onFetchDataSuccess(List<Track> data) {
        Log.d("TAG", "onFetchDataSuccess: "+data.get(0).getTitle());
        mView.showListTrack(data);
    }

    @Override
    public void onFetchDataFail(String message) {

    }
}
