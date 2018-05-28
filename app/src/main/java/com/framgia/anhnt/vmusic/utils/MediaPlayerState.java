package com.framgia.anhnt.vmusic.utils;

import android.support.annotation.IntDef;

public interface MediaPlayerState {
    int IDLE = 0;
    int PLAYING = 1;
    int PREPARED=2;
    int PAUSED = 3;
    int STOPPED = 4;
    int ERROR = 5;
    int LOOP_NO = 0;
    int LOOP_ONE = 1;
    int LOOP_ALL = 2;
    int SHUFFLE = 1;
    int NO_SHUFFLE = 0;

    @IntDef({IDLE, PLAYING, PAUSED, STOPPED, ERROR})
    public @interface MediaState {
    }

    @IntDef({LOOP_NO, LOOP_ONE, LOOP_ALL})
    public @interface LoopState {
    }

    @IntDef({SHUFFLE, NO_SHUFFLE})
    public @interface ShuffleState {
    }
}
