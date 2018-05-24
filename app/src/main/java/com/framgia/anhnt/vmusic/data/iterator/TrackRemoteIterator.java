package com.framgia.anhnt.vmusic.data.iterator;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TrackRemoteIterator extends TrackRemote<List<Track>> {
    public TrackRemoteIterator(TrackDataSource.OnFetchDataListener listener) {
        super(listener);
    }

    @Override
    public List<Track> getData(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        return new TrackRemoteHandle().getListTrack(jsonObject);
    }
}
