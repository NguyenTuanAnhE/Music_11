package com.framgia.anhnt.vmusic.data.iterator;

import android.support.annotation.Nullable;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.anhnt.vmusic.utils.Constants.ApiRequest.CONNECT_TIMEOUT;
import static com.framgia.anhnt.vmusic.utils.Constants.ApiRequest.READ_TIMEOUT;
import static com.framgia.anhnt.vmusic.utils.Constants.ApiRequest.REQUEST_METHOD;

public class TrackRemoteHandle {

    public TrackRemoteHandle() {

    }

    @Nullable
    public String getJsonFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setDoOutput(true);
        connection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        connection.disconnect();
        return sb.toString();
    }

    public Track getTrack(JSONObject jsonObject) throws JSONException {
        JSONObject user = jsonObject.optJSONObject(Track.TrackEntry.USER);
        String artworkUrl = jsonObject.optString(Track.TrackEntry.ARTWORK_URL);
        String username = null;
        String userAvatar = null;
        if (user != null) {
            username = user.optString(Track.TrackEntry.USERNAME);
            userAvatar = user.optString(Track.TrackEntry.AVATAR_URL);

            if (artworkUrl.equals(Constants.ApiRequest.NULL)) {
                artworkUrl = userAvatar;
            }
        }
        Track track = new Track.TrackBuilder()
                .setArtworkUrl(artworkUrl)
                .setAvatarUrl(userAvatar)
                .setUsername(username)
                .setDescription(jsonObject.optString(Track.TrackEntry.DESCRIPTION))
                .setDownloadable(jsonObject.optBoolean(Track.TrackEntry.DOWNLOADABLE))
                .setDownloadUrl(jsonObject.optString(Track.TrackEntry.DOWNLOAD_URL))
                .setDuration(jsonObject.optLong(Track.TrackEntry.DURATION))
                .setId(jsonObject.optInt(Track.TrackEntry.ID))
                .setLikesCount(jsonObject.optInt(Track.TrackEntry.LIKES_COUNT))
                .setPlaybackCount(jsonObject.optInt(Track.TrackEntry.PLAYBACK_COUNT))
                .setTitle(jsonObject.optString(Track.TrackEntry.TITLE))
                .setUri(jsonObject.optString(Track.TrackEntry.URI))
                .build();

        return track;
    }

    public List<Track> getListTrack(JSONObject jsonObject) throws JSONException {
        List<Track> tracks = new ArrayList<>();
        JSONArray listTracks = jsonObject.getJSONArray(Track.TrackEntry.COLLECTION);
        for (int i = 0; i < listTracks.length(); i++) {
            JSONObject object = listTracks.getJSONObject(i)
                    .getJSONObject(Track.TrackEntry.TRACK);
            Track song = getTrack(object);
            if (song != null) {
                tracks.add(song);
            }
        }
        return tracks;
    }
}
