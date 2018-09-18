package com.framgia.anhnt.vmusic.service;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.utils.MediaPlayerState;

public interface MediaServiceListener {
    void onFail(String error);

    void onChangeMediaState(@MediaPlayerState.MediaState int mediaState);

    void playTrack(Track track);

    void onShuffle(int shuffle);

    void onLoop(int loop);
}
