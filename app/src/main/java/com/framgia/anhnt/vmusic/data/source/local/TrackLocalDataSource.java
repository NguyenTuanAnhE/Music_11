package com.framgia.anhnt.vmusic.data.source.local;

import android.content.Context;

import com.framgia.anhnt.vmusic.data.iterator.GetTrackLocalIterator;
import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;

import java.util.List;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    private static TrackLocalDataSource sTrackTrackLocalDataSource;
    private GetTrackLocalIterator mGetTrackLocalIterator;

    public static TrackLocalDataSource getInstance() {

        return sTrackTrackLocalDataSource;
    }

    public static void initContext(Context context) {
        if (sTrackTrackLocalDataSource == null) {
            sTrackTrackLocalDataSource = new TrackLocalDataSource(context);
        }
    }

    private TrackLocalDataSource(Context context) {
        mGetTrackLocalIterator = new GetTrackLocalIterator(context);
    }

    @Override
    public void getLocalTrack(TrackDataSource.OnFetchDataListener<List<Track>> listener) {
        mGetTrackLocalIterator.getTracks(listener);
    }
}
