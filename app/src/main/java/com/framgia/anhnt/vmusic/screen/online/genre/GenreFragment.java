package com.framgia.anhnt.vmusic.screen.online.genre;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framgia.anhnt.vmusic.BaseFragment;
import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.repositories.TrackRepository;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;
import com.framgia.anhnt.vmusic.data.source.local.TrackLocalDataSource;
import com.framgia.anhnt.vmusic.data.source.remote.TrackRemoteDataSource;
import com.framgia.anhnt.vmusic.utils.Constants;
import com.framgia.anhnt.vmusic.utils.GenreType;

import java.util.List;

public class GenreFragment extends BaseFragment implements GenreContract.View {
    private RecyclerView mRecyclerTrack;
    private TrackAdapter mTrackAdapter;
    private GenreContract.Presenter mPresenter;

    public static GenreFragment newInstance(String genre) {
        GenreFragment genreFragment = new GenreFragment();
        Bundle args = new Bundle();
        args.putString(GenreType.ARGUMENT_GENRE, genre);
        genreFragment.setArguments(args);
        return genreFragment;
    }

    public GenreFragment() {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_genre;
    }

    @Override
    protected void initComponents() {
        mRecyclerTrack = getView().findViewById(R.id.recycler_track);
        mTrackAdapter = new TrackAdapter(getContext());

        TrackDataSource.LocalDataSource localDataSource =
                TrackLocalDataSource.getInstance();
        TrackDataSource.RemoteDataSource remoteDataSource =
                TrackRemoteDataSource.getInstance();
        TrackRepository trackRepository =
                TrackRepository.getInstance(localDataSource, remoteDataSource);

        mPresenter = new GenrePresenter(trackRepository);
        mPresenter.setView(this);
        mRecyclerTrack.setAdapter(mTrackAdapter);
        mRecyclerTrack.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void loadData() {
        String genre = getArguments().getString(GenreType.ARGUMENT_GENRE);
        mPresenter.loadTrackByGenre(genre,
                Constants.ApiRequest.LIMIT_VALUE, Constants.ApiRequest.OFFSET_VALUE);
    }

    @Override
    public void showListTrack(List<Track> tracks) {
        mTrackAdapter.addData(tracks);
    }
}