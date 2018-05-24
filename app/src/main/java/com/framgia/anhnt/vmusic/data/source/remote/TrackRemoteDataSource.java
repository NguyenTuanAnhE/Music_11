package com.framgia.anhnt.vmusic.data.source.remote;

import com.framgia.anhnt.vmusic.BuildConfig;
import com.framgia.anhnt.vmusic.data.iterator.TrackRemoteIterator;
import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;
import com.framgia.anhnt.vmusic.utils.Constants;
import com.framgia.anhnt.vmusic.utils.TrackUtils;

import java.util.HashMap;
import java.util.List;

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {
    private static TrackRemoteDataSource sTrackTrackRemoteDataSource;

    public static TrackRemoteDataSource getInstance() {
        if (sTrackTrackRemoteDataSource == null) {
            sTrackTrackRemoteDataSource = new TrackRemoteDataSource();
        }
        return sTrackTrackRemoteDataSource;
    }

    @Override
    public void getRemoteTrack(String genre, int limit, int offset,
                               TrackDataSource.OnFetchDataListener<List<Track>> listener) {
        HashMap<String, String> params = new HashMap<>();

        params.put(Constants.ApiRequest.OFFSET, String.valueOf(offset));
        params.put(Constants.ApiRequest.LIMIT, String.valueOf(limit));
        params.put(Constants.ApiRequest.CLIENT_ID, BuildConfig.API_KEY);
        params.put(Constants.ApiRequest.PARAMETER_GENRE, genre);

        String url = TrackUtils.makeUrl(Constants.ApiRequest.HOST, params);
        new TrackRemoteIterator(listener).execute(url);
    }
}
