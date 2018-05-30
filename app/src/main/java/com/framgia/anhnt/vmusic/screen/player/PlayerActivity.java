package com.framgia.anhnt.vmusic.screen.player;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.anhnt.vmusic.BaseActivity;
import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.repositories.TrackRepository;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;
import com.framgia.anhnt.vmusic.data.source.local.TrackLocalDataSource;
import com.framgia.anhnt.vmusic.data.source.remote.TrackRemoteDataSource;
import com.framgia.anhnt.vmusic.service.MediaService;
import com.framgia.anhnt.vmusic.service.MediaServiceListener;
import com.framgia.anhnt.vmusic.utils.TrackUtils;

import java.util.List;

import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.LOOP_ALL;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.LOOP_NO;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.LOOP_ONE;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.NO_SHUFFLE;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PAUSED;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PLAYING;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PREPARED;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PREPARING;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.SHUFFLE;

public class PlayerActivity extends BaseActivity implements PlayerContract.View,
        View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaServiceListener,
        PlayerAdapter.OnTrackClickListener {
    public static final String ARGUMENT_GENRE = "genre";
    public static final String ARGUMENT_NEW_TRACK = "new track";
    private static final long DELAY_TIME = 10;
    private Toolbar mToolbar;
    private ImageView mImageArtwork;
    private TextView mTextCurrentDuration;
    private TextView mTextDuration;
    private SeekBar mSeekBarDuration;
    private TextView mTextTitle;
    private TextView mTextArtist;
    private TextView mTextLoopOne;
    private ProgressBar mProgressLoading;
    private ImageButton mButtonPlayPause;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonShuffle;
    private ImageButton mButtonLoop;
    private RecyclerView mRecyclerPlayer;

    private PlayerAdapter mPlayerAdapter;
    private PlayerPresenter mPresenter;

    private Handler mHandler;
    private List<Track> mTracks;
    private int mPosition;
    private MediaService mMediaService;
    private boolean mIsBound;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            mMediaService = binder.getService();
            mMediaService.setListener(PlayerActivity.this);
            mIsBound = true;

            getData();
            updatePlayingDetail(mMediaService.getCurrentTrack());

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mIsBound = false;
        }
    };

    public static Intent getPlayerIntent(Context context) {
        return new Intent(context, PlayerActivity.class);
    }

    public static Intent getPlayerIntent(Context context, int position) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(ARGUMENT_GENRE, TrackUtils.getGenre(position));
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MediaService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaService.removeListener(this);
        unbindService(mConnection);
        mIsBound = false;
    }

    @Override
    protected boolean getFullScreen() {
        return false;
    }

    @Override

    protected boolean showHomeEnable() {
        return true;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_music_player;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.color_free_speech_red;
    }

    @Override
    public void initComponents() {
        initView();
        initComponent();
        initListener();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mImageArtwork = findViewById(R.id.image_artwork);
        mTextCurrentDuration = findViewById(R.id.text_current);
        mTextDuration = findViewById(R.id.text_duration);
        mSeekBarDuration = findViewById(R.id.seek_bar_duration);
        mTextTitle = findViewById(R.id.text_title);
        mTextArtist = findViewById(R.id.text_artist);
        mTextLoopOne = findViewById(R.id.text_loop_one);
        mProgressLoading = findViewById(R.id.progress_loading);
        mButtonPlayPause = findViewById(R.id.image_play_pause);
        mButtonNext = findViewById(R.id.image_next);
        mButtonPrevious = findViewById(R.id.image_previous);
        mButtonShuffle = findViewById(R.id.image_shuffle);
        mButtonLoop = findViewById(R.id.image_loop);
        mRecyclerPlayer = findViewById(R.id.recycler_player);
        mSeekBarDuration.setFocusable(false);

        setSupportActionBar(mToolbar);
        setTitle(R.string.player);
    }

    private void initComponent() {
        TrackDataSource.LocalDataSource localDataSource =
                TrackLocalDataSource.getInstance();
        TrackDataSource.RemoteDataSource remoteDataSource =
                TrackRemoteDataSource.getInstance();
        TrackRepository trackRepository =
                TrackRepository.getInstance(localDataSource, remoteDataSource);
        mPresenter = new PlayerPresenter(trackRepository);
        mPresenter.setView(this);

        mPlayerAdapter = new PlayerAdapter(this);
        mPlayerAdapter.setListener(this);
        mRecyclerPlayer.setAdapter(mPlayerAdapter);
        mRecyclerPlayer.setLayoutManager(new LinearLayoutManager(this));

        mHandler = new Handler();
    }

    private void initListener() {
        mButtonPlayPause.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonPrevious.setOnClickListener(this);
        mButtonShuffle.setOnClickListener(this);
        mButtonLoop.setOnClickListener(this);
        mSeekBarDuration.setOnSeekBarChangeListener(this);
    }

    private void getData() {
        mPosition = mMediaService.getCurrentPosition();
        mTracks = mMediaService.getTracks();
        mPlayerAdapter.addData(mTracks);
        mPlayerAdapter.setPosition(mPosition);
    }

    private void updatePlayingDetail(Track track) {
        updateTrackDetail(track);
        updateSeekbar();
        updateControlButton(track);
    }

    private void updateTrackDetail(Track track) {
        mTextTitle.setText(track.getTitle());
        mTextArtist.setText(track.getUsername());
        mTextDuration.setText(TrackUtils.getDuration(track.getDuration()));
        mSeekBarDuration.setMax((int) track.getDuration());
        Glide.with(getApplicationContext())
                .load(track.getArtworkUrl())
                .into(mImageArtwork);
    }

    private void updateSeekbar() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextCurrentDuration.setText(TrackUtils.getDuration(mMediaService.getCurrentDuration()));
                mSeekBarDuration.setProgress((int) mMediaService.getCurrentDuration());
                mHandler.postDelayed(this, DELAY_TIME);
            }
        }, 0);
    }

    private void updateControlButton(Track track) {
        int position = mTracks == null ?
                mMediaService.getTracks().indexOf(track) : mTracks.indexOf(track);
        updateNowPlayingList(position);
        updatePlayPauseButton(mMediaService.getState());
        if (getIntent() != null && getIntent().getAction() != null) {
            loadSongSuccess();
        }
        setShuffle(mMediaService.getShuffle());
        setLoop(mMediaService.getLoop());
    }

    private void updatePlayPauseButton(int state) {
        switch (state) {
            case PREPARING:
                loadSongProgress();
                break;
            case PAUSED:
            case PREPARED:
                loadTrackSuccess(R.drawable.ic_play);
                break;
            case PLAYING:
                loadTrackSuccess(R.drawable.ic_pause);
                break;
        }
    }

    private void loadTrackSuccess(int resId) {
        mButtonPlayPause.setBackgroundResource(resId);
        mProgressLoading.setVisibility(View.INVISIBLE);
        mButtonPlayPause.setVisibility(View.VISIBLE);
    }

    private void playPauseSong() {
        if (mMediaService == null) return;
        mMediaService.playPauseTrack();
    }

    private void nextTrack() {
        if (mMediaService == null) return;
        mMediaService.playNextTrack();
    }

    private void previousTrack() {
        if (mMediaService == null) return;
        mMediaService.playPreviousTrack();
    }

    private void shuffleSong() {
        if (mMediaService == null) return;
        mMediaService.shuffleSong();
    }

    private void loopSong() {
        if (mMediaService == null) return;
        mMediaService.loopSong();
    }

    private void loadSongProgress() {
        mProgressLoading.setVisibility(View.VISIBLE);
        mButtonPlayPause.setVisibility(View.INVISIBLE);
    }

    private void loadSongSuccess() {
        mProgressLoading.setVisibility(View.INVISIBLE);
        mButtonPlayPause.setVisibility(View.VISIBLE);
        mSeekBarDuration.setFocusable(true);
    }

    private void setShuffle(int shuffle) {
        switch (shuffle) {
            case NO_SHUFFLE:
                mButtonShuffle.setBackground(getResources().getDrawable(R.drawable.ic_shuffle));
                break;
            case SHUFFLE:
                mButtonShuffle.setBackground(getResources().getDrawable(R.drawable.ic_shuffle_active));
                break;
        }
    }

    private void setLoop(int loop) {
        switch (loop) {
            case LOOP_NO:
                mButtonLoop.setBackground(getResources().getDrawable(R.drawable.ic_loop));
                mTextLoopOne.setVisibility(View.INVISIBLE);
                break;
            case LOOP_ONE:
                mButtonLoop.setBackground(getResources().getDrawable(R.drawable.ic_loop_active));
                mTextLoopOne.setVisibility(View.VISIBLE);
                break;
            case LOOP_ALL:
                mButtonLoop.setBackground(getResources().getDrawable(R.drawable.ic_loop_active));
                mTextLoopOne.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void updateNowPlayingList(int position) {
        if (position < 0) return;
        mPlayerAdapter.setPosition(position);
        mRecyclerPlayer.scrollToPosition(position);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_play_pause:
                playPauseSong();
                break;
            case R.id.image_next:
                nextTrack();
                break;
            case R.id.image_previous:
                previousTrack();
                break;
            case R.id.image_shuffle:
                shuffleSong();
                break;
            case R.id.image_loop:
                loopSong();
                break;
        }
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        loadSongSuccess();
    }

    @Override
    public void onChangeMediaState(int mediaState) {
        updatePlayPauseButton(mediaState);
    }

    @Override
    public void playTrack(Track track) {
        updatePlayingDetail(track);
    }

    @Override
    public void onShuffle(int shuffle) {
        setShuffle(shuffle);
    }

    @Override
    public void onLoop(int loop) {
        setLoop(loop);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mTextCurrentDuration.setText(TrackUtils.getDuration(seekBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mMediaService.seekTo(seekBar.getProgress());
        mTextCurrentDuration.setText(TrackUtils.getDuration(mMediaService.getCurrentDuration()));
    }

    @Override
    public void onTrackClick(int position) {
        if (!mMediaService.isNewSong(mTracks.get(position))) {
            return;
        }
        mPlayerAdapter.setPosition(position);
        loadSongProgress();
        mMediaService.playTrack(position);
    }
}
