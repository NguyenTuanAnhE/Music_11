package com.framgia.anhnt.vmusic.data.source;

import com.framgia.anhnt.vmusic.data.model.Track;

import java.util.List;

public interface TrackDataSource {
    interface LocalDataSource {
        void getLocalTrack(OnFetchDataListener<Track> listener);
    }

    interface RemoteDataSource {
        void getRemoteTrack(String genre, int limit, int offset,
                            OnFetchDataListener<List<Track>> listener);

        void searchTrack(int limit, String key, OnFetchDataListener<List<Track>> listener);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(T data);

        void onFetchDataFail(String message);
    }
}
