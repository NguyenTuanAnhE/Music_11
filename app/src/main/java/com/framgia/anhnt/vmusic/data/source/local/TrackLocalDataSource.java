package com.framgia.anhnt.vmusic.data.source.local;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    private static TrackLocalDataSource sTrackTrackLocalDataSource;

    public static TrackLocalDataSource getInstance() {
        if (sTrackTrackLocalDataSource == null) {
            sTrackTrackLocalDataSource = new TrackLocalDataSource();
        }
        return sTrackTrackLocalDataSource;
    }

    @Override
    public void getLocalTrack(TrackDataSource.OnFetchDataListener<Track> listener) {

    }
}
