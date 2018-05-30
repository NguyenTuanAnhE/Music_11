package com.framgia.anhnt.vmusic.data.repositories;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;

import java.util.List;

public class TrackRepository implements TrackDataSource.LocalDataSource,
        TrackDataSource.RemoteDataSource {

    private static TrackRepository sTrackRepository;
    private TrackDataSource.LocalDataSource mLocalDataSource;
    private TrackDataSource.RemoteDataSource mRemoteDataSource;

    public static TrackRepository getInstance(TrackDataSource.LocalDataSource localDataSource,
                                              TrackDataSource.RemoteDataSource remoteDataSource) {
        if (sTrackRepository == null) {
            sTrackRepository = new TrackRepository(localDataSource, remoteDataSource);
        }
        return sTrackRepository;
    }

    private TrackRepository(TrackDataSource.LocalDataSource localDataSource,
                            TrackDataSource.RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public void getLocalTrack(TrackDataSource.OnFetchDataListener<Track> listener) {

    }

    @Override
    public void getRemoteTrack(String genre, int limit, int offset,
                               TrackDataSource.OnFetchDataListener<List<Track>> listener) {
        mRemoteDataSource.getRemoteTrack(genre, limit, offset, listener);
    }

    @Override
    public void searchTrack(int limit, String key, TrackDataSource.OnFetchDataListener<List<Track>> listener) {
        mRemoteDataSource.searchTrack(limit, key, listener);
    }

}
