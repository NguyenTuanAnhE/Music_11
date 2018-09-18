package com.framgia.anhnt.vmusic.screen.offline;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import com.framgia.anhnt.vmusic.screen.online.OnlineActivity;
import com.framgia.anhnt.vmusic.screen.player.PlayerActivity;
import com.framgia.anhnt.vmusic.screen.search.SearchActivity;
import com.framgia.anhnt.vmusic.service.MediaService;
import com.framgia.anhnt.vmusic.service.MediaServiceListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.IDLE;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PLAYING;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PREPARING;

public class OfflineActivity extends BaseActivity implements OfflineContract.View,
        OfflineAdapter.OnTrackClickListener, View.OnClickListener, MediaServiceListener {
    private View mToolBar;
    private TextView mTextTitleToolbar;
    private RecyclerView mRecyclerLocal;
    private View mLayoutBottom;
    private TextView mTextTitleSmall;
    private TextView mTextArtistSmall;
    private RoundedImageView mImageArtworkSmall;
    private ImageButton mImagePlay;
    private ProgressBar mProgressLoading;
    private OfflineAdapter mOfflineAdapter;
    private OfflinePresenter mPresenter;
    private List<Track> mTracks;
    private MediaService mMediaService;
    private boolean mIsBound;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            mMediaService = binder.getService();
            mMediaService.setListener(OfflineActivity.this);
            mIsBound = true;
            if (mMediaService.getState() != IDLE) {
                showSmallControlView(mMediaService.getCurrentTrack());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mIsBound = false;
        }
    };

    public static Intent getOfflineIntent(Context context) {
        Intent intent = new Intent(context, OfflineActivity.class);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MediaService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsBound) {
            showSmallControlView(mMediaService.getCurrentTrack());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaService.removeListener(this);
        unbindService(mConnection);
        mIsBound = false;
    }

    private void showSmallControlView(Track track) {
        mLayoutBottom.setVisibility(View.VISIBLE);
        updateDataSmallView(track);
        updatePlayButton(mMediaService.getState());
    }

    private void updateDataSmallView(Track track) {
        mTextTitleSmall.setText(track.getTitle());
        mTextArtistSmall.setText(track.getUsername());
        Glide.with(getApplicationContext())
                .load(track.getArtworkUrl())
                .into(mImageArtworkSmall);
    }

    private void updatePlayButton(int mediaState) {
        switch (mediaState) {
            case PREPARING:
                loadSongProgress();
                break;
            case PLAYING:
                loadTrackSuccess(R.drawable.ic_pause);
                break;
            default:
                loadTrackSuccess(R.drawable.ic_play);
                break;
        }
    }

    private void loadSongProgress() {
        mProgressLoading.setVisibility(View.VISIBLE);
        mImagePlay.setVisibility(View.INVISIBLE);
    }

    private void loadTrackSuccess(int resId) {
        mImagePlay.setBackgroundResource(resId);
        mProgressLoading.setVisibility(View.INVISIBLE);
        mImagePlay.setVisibility(View.VISIBLE);
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

    private void loadSongSuccess() {
        mProgressLoading.setVisibility(View.INVISIBLE);
        mImagePlay.setVisibility(View.VISIBLE);
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
        return R.layout.activity_offline;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.color_ocean_blue;
    }

    @Override
    public void initComponents() {
        mLayoutBottom = findViewById(R.id.include_small_control);
        mToolBar = findViewById(R.id.include_toolbar);
        mTextTitleToolbar = findViewById(R.id.text_title_toolbar);
        mToolBar.setBackgroundColor(getResources()
                .getColor(R.color.color_dodger_blue));
        mTextTitleToolbar.setText(getResources().getString(R.string.my_music));
        mRecyclerLocal = findViewById(R.id.recycler_local);
        mImageArtworkSmall = findViewById(R.id.image_artwork_small);
        mImagePlay = findViewById(R.id.image_small_play);
        mTextArtistSmall = findViewById(R.id.text_artist_small);
        mTextTitleSmall = findViewById(R.id.text_title_small);
        mProgressLoading = findViewById(R.id.progress_small_loading);
        mLayoutBottom.setBackgroundColor(getResources()
                .getColor(R.color.color_ocean_blue));
        mLayoutBottom.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.image_search_background).setOnClickListener(this);
        findViewById(R.id.image_small_next).setOnClickListener(this);
        findViewById(R.id.image_small_previous).setOnClickListener(this);
        findViewById(R.id.image_search_background).setOnClickListener(this);

        TrackDataSource.LocalDataSource localDataSource =
                TrackLocalDataSource.getInstance();
        TrackDataSource.RemoteDataSource remoteDataSource =
                TrackRemoteDataSource.getInstance();
        TrackRepository trackRepository =
                TrackRepository.getInstance(localDataSource, remoteDataSource);
        mPresenter = new OfflinePresenter(trackRepository);
        mPresenter.setView(this);

        mOfflineAdapter = new OfflineAdapter(this);
        mOfflineAdapter.setListener(this);

        mRecyclerLocal.setAdapter(mOfflineAdapter);
        mRecyclerLocal.setLayoutManager(new LinearLayoutManager(this));

        mPresenter.getLocalTracks();
    }

    @Override
    public void showListTracks(List<Track> tracks) {
        mTracks = tracks;
        mOfflineAdapter.addData(tracks);
    }

    @Override
    public void onTrackClick(int position) {
        startService(MediaService.getMediaServiceIntent(this,
                mTracks, position));
        startActivity(PlayerActivity.getPlayerIntent(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                onBackPressed();
                break;
            case R.id.image_search_background:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.image_small_previous:
                previousTrack();
                break;
            case R.id.image_small_play:
                playPauseSong();
                break;
            case R.id.image_small_next:
                nextTrack();
                break;
            case R.id.include_small_control:
                mMediaService.setNewSong(false);
                startActivity(PlayerActivity.getPlayerIntent(this));
                break;
            default:
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
        updatePlayButton(mediaState);
    }

    @Override
    public void playTrack(Track track) {
        updateDataSmallView(track);
    }

    @Override
    public void onShuffle(int shuffle) {

    }

    @Override
    public void onLoop(int loop) {

    }
}
