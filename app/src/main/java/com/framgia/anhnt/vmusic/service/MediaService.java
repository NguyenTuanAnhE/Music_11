package com.framgia.anhnt.vmusic.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.utils.media.MediaManager;

import java.util.ArrayList;
import java.util.List;

public class MediaService extends Service implements MediaManager.OnMediaListener {
    public static final String ACTION_START_MUSIC = "start music";
    private static final String ARGUMENT_POSITION = "position";
    private static final String ARGUMENT_LIST = "list track";
    private final int NOTIFICATION_MEDIA = 21;
    private final IBinder mBinder = new MediaBinder();
    private MediaManager mMediaManager;
    private int mPosition;
    private List<Track> mTracks;
    private Notification mNotification;
    private OnMediaListener mListener;

    public static Intent getMediaServiceIntent(Context context, List<Track> tracks, int position) {
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(ACTION_START_MUSIC);
        intent.putParcelableArrayListExtra(ARGUMENT_LIST,
                (ArrayList<? extends Parcelable>) tracks);
        intent.putExtra(ARGUMENT_POSITION, position);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaManager = new MediaManager(getApplicationContext());
        mMediaManager.setListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_STICKY;
    }

    public void setListener(OnMediaListener listener) {
        mListener = listener;
    }

    public int getCurrentPosition() {
        return mMediaManager.getCurrentPosition();
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public Track getCurrentTrack() {
        return mMediaManager.getCurrentTrack();
    }

    public int getState() {
        return mMediaManager.getState();
    }

    public int getLoop() {
        return mMediaManager.getLoop();
    }

    public int getShuffle() {
        return mMediaManager.getShuffle();
    }

    public long getCurrentDuration() {
        return mMediaManager.getCurrentDuration();
    }

    /**
     * Return true if click on a playing track
     */
    public boolean isNewSong() {
        return mMediaManager.isNewSong();
    }

    /**
     * Handle intent received to control music
     */
    private void handleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        switch (intent.getAction()) {
            //handle intent start a track
            case ACTION_START_MUSIC:
                mPosition = intent.getIntExtra(ARGUMENT_POSITION, 0);
                mTracks = intent.getParcelableArrayListExtra(ARGUMENT_LIST);
                playTrack(mTracks, mPosition);
                break;
        }
    }

    /**
     * Play track when click on a  item in list tracks
     */
    public void playTrack(List<Track> tracks, int position) {
        if (mMediaManager == null) return;
        mMediaManager.playTrack(tracks, position);
    }

    /**
     * Play track with position of item in list
     */
    public void playTrack(int position) {
        if (mMediaManager == null) return;
        mMediaManager.playTrack(position);
    }

    /**
     * Play or pause a track if track is played
     */
    public void playPauseTrack() {
        mMediaManager.playOrPause();
    }

    /**
     * Play next track
     */
    public void playNextTrack() {
        mMediaManager.playNextTrack();
    }

    /**
     * Play previous track
     */
    public void playPreviousTrack() {
        mMediaManager.playPreviousTrack();
    }

    /**
     * Shuffle list track
     */
    public void shuffleSong() {
        mMediaManager.switchShuffleState();
    }

    /**
     * Loop if a track play complete by loop mode
     */
    public void loopSong() {
        mMediaManager.loopTrack();
    }

    /**
     * Seek to position
     */
    public void seekTo(int position) {
        mMediaManager.seekTo(position);
    }

    /**
     * Callback when get error while playing track
     */
    @Override
    public void onFail(String error) {
        mListener.onFail(error);
    }

    /**
     * Callback when preparing play a track
     */
    @Override
    public void onPreparing() {
        mListener.onPreparing();
    }

    /**
     * Callback when pause play a track
     */
    @Override
    public void onPause(Track track, boolean reLoad) {
        mListener.onPauseTrack(track, reLoad);
    }

    /**
     * Callback when play play a track
     */
    @Override
    public void onPlay(Track track, boolean reLoad) {
        mListener.onPlayTrack(track, reLoad);
    }

    /**
     * Callback when a track is prepared success to play
     */
    @Override
    public void onPrepared(Track track) {
        mListener.onPrepared(track);
    }

    /**
     * Callback when shuffle mode change
     */
    @Override
    public void onShuffle(int shuffle) {
        mListener.onShuffle(shuffle);
    }

    /**
     * Callback when loop mode change
     */
    @Override
    public void onLoop(int loop) {
        mListener.onLoop(loop);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaManager == null) return;
        mMediaManager.destroy();
    }

    public class MediaBinder extends Binder {
        public MediaService getService() {
            return MediaService.this;
        }
    }

    public interface OnMediaListener {
        void onFail(String error);

        void onPreparing();

        void onPauseTrack(Track track, boolean reLoad);

        void onPlayTrack(Track track, boolean reLoad);

        void onPrepared(Track track);

        void onShuffle(int shuffle);

        void onLoop(int loop);
    }
}
