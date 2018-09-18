package com.framgia.anhnt.vmusic.data.iterator;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class SearchRemoteIterator extends TrackRemote<List<Track>> {
    public SearchRemoteIterator(TrackDataSource.OnFetchDataListener listener) {
        super(listener);
    }

    @Override
    public List<Track> getData(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        return new TrackRemoteHandle().getSearchList(jsonArray);
    }
}
