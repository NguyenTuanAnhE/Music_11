package com.framgia.anhnt.vmusic.utils.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.data.model.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.ERROR;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.IDLE;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PLAYING;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PAUSED;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PREPARED;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.STOPPED;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.LOOP_NO;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.LOOP_ONE;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.LOOP_ALL;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.NO_SHUFFLE;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.SHUFFLE;

public class MediaManager implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private int mState;
    private int mLoop;
    private int mShuffle;
    private int mCurrentPosition;
    private boolean mEndOfList;
    private boolean mNewSong;
    private List<Track> mTracks;
    private List<Track> mShuffleTracks;
    private Track mCurrentTrack;
    private OnMediaListener mListener;

    public MediaManager(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
        mTracks = new ArrayList<>();
        mShuffleTracks = new ArrayList<>();
        mState = IDLE;
    }

    public void setListener(OnMediaListener listener) {
        mListener = listener;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        mCurrentPosition = currentPosition;
    }

    public Track getCurrentTrack() {
        return mTracks.get(mCurrentPosition);
    }

    public int getState() {
        return mState;
    }

    public int getLoop() {
        return mLoop;
    }

    public int getShuffle() {
        return mShuffle;
    }

    public long getCurrentDuration() {
        if (mMediaPlayer == null) return 0;
        return mMediaPlayer.getCurrentPosition();
    }

    public boolean isNewSong() {
        return mNewSong;
    }

    /**
     * Play track when click on a  item in list tracks
     */
    public void playTrack(List<Track> tracks, int position) {
        //handle empty list
        if (tracks.size() == 0) {
            mState = ERROR;
            mListener.onFail(mContext.getString(R.string.error_empty_list));
            return;
        }
        if (mTracks != null && mTracks.size() != 0) {
            //if tracked is selected to play is playing
            if (mCurrentTrack.getId() == tracks.get(position).getId()) {
                mNewSong = false;
                return;
            }
        }
        //play a new track
        mNewSong = true;
        mTracks.clear();
        mTracks.addAll(tracks);
        mCurrentPosition = position;
        prepareTrack();
    }

    /**
     * Play track with position of item in list
     */
    public void playTrack(int position) {
        mCurrentPosition = position;
        //update ui
        mListener.onPlay(getCurrentTrack(), true);
        prepareTrack();
    }

    public void prepareTrack() {
        //if error reset media player to idle state
        if (mState != ERROR) {
            reset();
        }
        //get current track(selected track)
        mCurrentTrack = getCurrentTrack();
        try {
            mMediaPlayer.setDataSource(mCurrentTrack.getUri());
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            mListener.onFail(e.getMessage());
        }
    }

    /**
     * Play or pause a track if track is played
     */
    public void playOrPause() {
        //if track is playing, pause track
        if (mState == PLAYING) {
            mMediaPlayer.pause();
            mState = PAUSED;
            mListener.onPause(getCurrentTrack(), false);
        } else if (mState == PAUSED || mState == STOPPED || mState == PREPARED) {
            //if track is paused or stopped or prepared, play track
            mMediaPlayer.start();
            mState = PLAYING;
            mListener.onPlay(mCurrentTrack, false);
        }
    }

    /**
     * Play next track
     */
    public void playNextTrack() {
        //update ui load progress
        mListener.onPreparing();
        //if current playing is end of list tracks
        if (mCurrentPosition == mTracks.size() - 1) {
            //if loop mode LOOP_ALL not active
            if (mLoop != LOOP_ALL) {
                mListener.onFail(mContext.getString(R.string.error_end_of_list));
                return;
            }
            //if LOOP_ALL  active
            mCurrentPosition = -1;
        }
        //go to playing next track
        mCurrentPosition++;
        mNewSong = true;
        //update detail of playing track
        mListener.onPlay(getCurrentTrack(), true);
        prepareTrack();
    }

    /**
     * Play previous track
     */
    public void playPreviousTrack() {
        //update ui progress loading
        mListener.onPreparing();
        //if current playing is end of list tracks, go to end of track
        if (mCurrentPosition == 0) {
            mCurrentPosition = mTracks.size();
        }
        //go to playing next track
        mCurrentPosition--;
        mNewSong = true;
        //update detail of playing track
        mListener.onPlay(getCurrentTrack(), true);
        prepareTrack();
    }

    /**
     * Shuffle list track
     */
    public void switchShuffleState() {
        //if shuffle mode SHUFFLE not active, shuffle track and save a list not shuffled
        if (mShuffle == NO_SHUFFLE) {
            mShuffleTracks.clear();
            mShuffleTracks.addAll(mTracks);
            Collections.shuffle(mTracks);
            mShuffle = SHUFFLE;
        } else {
            /**
             * get position of playing track in list track not shuffled,
             * get list track from list not shuffled
             */
            int currentPosition = mShuffleTracks.indexOf(getCurrentTrack());
            if (currentPosition >= 0) {
                mCurrentPosition = currentPosition;
                mTracks.clear();
                mTracks.addAll(mShuffleTracks);
                mShuffle = NO_SHUFFLE;
            }
        }
        //update ui shuffle button
        mListener.onShuffle(mShuffle);
    }

    public void loopTrack() {
        //set loop mode
        switch (mLoop) {
            case LOOP_NO:
                mLoop = LOOP_ONE;
                break;
            case LOOP_ONE:
                mMediaPlayer.setLooping(false);
                mLoop = LOOP_ALL;
                break;
            case LOOP_ALL:
                mLoop = LOOP_NO;
                break;
        }
        //update ui loop button
        mListener.onLoop(mLoop);
    }

    /**
     * Play next track when loop not active
     */
    private void loopNoTracks() {
        //if playing track is not end of list, play next track
        if (getCurrentPosition() != getTracks().size() - 1) {
            playNextTrack();
            mNewSong = true;
            //update ui progress loading
            mListener.onPreparing();
        } else {
            //if playing track is not end of list, go to head of list
            mCurrentPosition = 0;
            //update ui play button, wait for play
            mListener.onPause(getCurrentTrack(), true);
            mEndOfList = true;
            prepareTrack();
        }
    }

    /**
     * Play a track again when complete
     */
    private void loopOneTrack() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    /**
     * Play list track again when track in the end of list play complete
     */
    private void loopAllTracks() {
        mNewSong = true;
        playNextTrack();
    }

    /**
     * Seek media player to a position to play
     */
    public void seekTo(int position) {
        if (mMediaPlayer == null) return;
        if (mState == PLAYING || mState == PAUSED) {
            mMediaPlayer.seekTo(position);
        }
    }

    /**
     * pause music
     */
    public void pause() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.pause();
    }

    /**
     * Reset media player
     */
    public void reset() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    /**
     * Destroy media player
     */
    public void destroy() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        //if LOOP_ALL not active
        if (mEndOfList) {
            //wait to user click play agian
            mState = PREPARED;
            mEndOfList = false;
        } else {
            mMediaPlayer.start();
            mState = PLAYING;
        }
        //update ui process loading
        mListener.onPrepared(mCurrentTrack);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //handle when a track play complete
        switch (mLoop) {
            case LOOP_NO:
                loopNoTracks();
                break;
            case LOOP_ONE:
                loopOneTrack();
                break;
            case LOOP_ALL:
                loopAllTracks();
                mListener.onPreparing();
                break;
        }
    }

    public interface OnMediaListener {
        void onFail(String error);

        void onPreparing();

        void onPause(Track track, boolean reLoad);

        void onPlay(Track track, boolean reLoad);

        void onPrepared(Track track);

        void onShuffle(int shuffle);

        void onLoop(int loop);
    }
}
