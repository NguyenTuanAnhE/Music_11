package com.framgia.anhnt.vmusic.screen.search;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.framgia.anhnt.vmusic.service.MediaService;
import com.framgia.anhnt.vmusic.service.MediaServiceListener;
import com.framgia.anhnt.vmusic.utils.Constants;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.IDLE;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PLAYING;
import static com.framgia.anhnt.vmusic.utils.MediaPlayerState.PREPARING;

public class SearchActivity extends BaseActivity implements SearchContract.View,
        SearchAdapter.OnTrackClickListener, SearchView.OnQueryTextListener, View.OnClickListener, MediaServiceListener {
    private SearchContract.Presenter mPresenter;
    private SearchAdapter mSearchAdapter;
    private List<Track> mTracks;
    private SearchView mSearchView;
    private ProgressBar mProgressLoad;
    private TextView mTextResult;
    private RecyclerView mRecyclerSearch;
    private Button mButtonBack;
    private View mLayoutBottom;
    private TextView mTextTitleSmall;
    private TextView mTextArtistSmall;
    private RoundedImageView mImageArtworkSmall;
    private ImageButton mImagePlay;
    private ProgressBar mProgressLoading;
    private MediaService mMediaService;
    private boolean mIsBound;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            mMediaService = binder.getService();
            mMediaService.setListener(SearchActivity.this);
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

    private void loadSongProgress() {
        mProgressLoading.setVisibility(View.VISIBLE);
        mImagePlay.setVisibility(View.INVISIBLE);
    }

    private void loadSongSuccess() {
        mProgressLoading.setVisibility(View.INVISIBLE);
        mImagePlay.setVisibility(View.VISIBLE);
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
    protected boolean getFullScreen() {
        return false;
    }

    @Override
    protected boolean showHomeEnable() {
        return true;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.color_ocean_blue;
    }

    @Override
    public void initComponents() {
        initView();
        initListener();
        initComponent();
    }

    private void initView() {
        mLayoutBottom = findViewById(R.id.include_small_control);
        mImageArtworkSmall = findViewById(R.id.image_artwork_small);
        mImagePlay = findViewById(R.id.image_small_play);
        mTextArtistSmall = findViewById(R.id.text_artist_small);
        mTextTitleSmall = findViewById(R.id.text_title_small);
        mProgressLoading = findViewById(R.id.progress_small_loading);
        mButtonBack = findViewById(R.id.button_back);
        mProgressLoad = findViewById(R.id.progress_loading);
        mTextResult = findViewById(R.id.text_no_result);
        mRecyclerSearch = findViewById(R.id.recycler_search);
        mSearchView = findViewById(R.id.search_track);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.requestFocusFromTouch();
        mLayoutBottom.setBackgroundColor(getResources()
                .getColor(R.color.color_ocean_blue));
    }

    private void initListener() {
        mSearchView.setOnQueryTextListener(this);
        mButtonBack.setOnClickListener(this);
        mLayoutBottom.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.image_small_next).setOnClickListener(this);
        findViewById(R.id.image_small_previous).setOnClickListener(this);
    }

    private void initComponent() {
        mSearchAdapter = new SearchAdapter(this);
        mSearchAdapter.setListener(this);

        TrackDataSource.LocalDataSource localDataSource =
                TrackLocalDataSource.getInstance();
        TrackDataSource.RemoteDataSource remoteDataSource =
                TrackRemoteDataSource.getInstance();
        TrackRepository trackRepository =
                TrackRepository.getInstance(localDataSource, remoteDataSource);

        mPresenter = new SearchPresenter(trackRepository);
        mPresenter.setView(this);
        mRecyclerSearch.setAdapter(mSearchAdapter);
        mRecyclerSearch.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showListTrack(List<Track> tracks) {
        if (tracks == null || tracks.size() == 0) {
            mProgressLoad.setVisibility(View.INVISIBLE);
            mTextResult.setVisibility(View.VISIBLE);
            mTracks.clear();
            mSearchAdapter.updateData(mTracks);
            return;
        }
        mSearchView.clearFocus();
        mProgressLoad.setVisibility(View.INVISIBLE);
        mTracks = tracks;
        mSearchAdapter.updateData(mTracks);

    }

    @Override
    public void showFailMessage() {
        mTextResult.setVisibility(View.VISIBLE);
        mProgressLoad.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(int position) {
        startService(MediaService.getMediaServiceIntent(this,
                mTracks, position));
        startActivity(PlayerActivity.getPlayerIntent(this));
    }

    @Override
    public void onClickDownload(int position) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPresenter.searchTrack(Constants.ApiRequest.LIMIT_VALUE, query);
        mTextResult.setVisibility(View.INVISIBLE);
        mProgressLoad.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                onBackPressed();
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
