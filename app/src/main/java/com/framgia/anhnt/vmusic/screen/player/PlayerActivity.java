package com.framgia.anhnt.vmusic.screen.player;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.anhnt.vmusic.BaseActivity;
import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.data.repositories.TrackRepository;
import com.framgia.anhnt.vmusic.data.source.TrackDataSource;
import com.framgia.anhnt.vmusic.data.source.local.TrackLocalDataSource;
import com.framgia.anhnt.vmusic.data.source.remote.TrackRemoteDataSource;
import com.framgia.anhnt.vmusic.utils.Constants;
import com.framgia.anhnt.vmusic.utils.GenreType;
import com.framgia.anhnt.vmusic.utils.IntentKey;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends BaseActivity implements PlayerContract.View {

    private Toolbar mToolbar;
    private ImageView mImageArtwork;
    private TextView mTextCurrentDuration;
    private TextView mTextDuration;
    private SeekBar mSeekBarDuration;
    private TextView mTextTitle;
    private TextView mTextArtist;
    private ImageButton mButtonPlayPause;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonShuffle;
    private ImageButton mButtonLoop;
    private RecyclerView mRecyclerPlayer;

    private PlayerAdapter mPlayerAdapter;
    private PlayerPresenter mPresenter;

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
        mToolbar = findViewById(R.id.toolbar);
        mImageArtwork = findViewById(R.id.image_artwork);
        mTextCurrentDuration = findViewById(R.id.text_current);
        mTextDuration = findViewById(R.id.text_duration);
        mSeekBarDuration = findViewById(R.id.seek_bar_duration);
        mTextTitle = findViewById(R.id.text_title);
        mTextArtist = findViewById(R.id.text_artist);
        mButtonPlayPause = findViewById(R.id.image_play_pause);
        mButtonNext = findViewById(R.id.image_next);
        mButtonPrevious = findViewById(R.id.image_previous);
        mButtonShuffle = findViewById(R.id.image_shuffle);
        mButtonLoop = findViewById(R.id.image_loop);
        mRecyclerPlayer = findViewById(R.id.recycler_player);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.player);

        TrackDataSource.LocalDataSource localDataSource =
                TrackLocalDataSource.getInstance();
        TrackDataSource.RemoteDataSource remoteDataSource =
                TrackRemoteDataSource.getInstance();
        TrackRepository trackRepository =
                TrackRepository.getInstance(localDataSource, remoteDataSource);
        mPresenter = new PlayerPresenter(trackRepository);
        mPresenter.setView(this);

        mPlayerAdapter = new PlayerAdapter(this);
        mRecyclerPlayer.setAdapter(mPlayerAdapter);
        mRecyclerPlayer.setLayoutManager(new LinearLayoutManager(this));

        getListTrack();
    }

    private void getListTrack() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        int position = intent.getIntExtra(IntentKey.SELECTED_POSITION, 0);
        ArrayList<Track> tracks = intent.getParcelableArrayListExtra(IntentKey.PARCELABLE_LIST);
        Track track = tracks.get(position);
        updatePlayingTrack(track);

//        String genre = intent.getStringExtra(GenreType.ARGUMENT_GENRE);
//        mPresenter.loadTrackByGenre(genre,
//                Constants.ApiRequest.LIMIT_VALUE, Constants.ApiRequest.OFFSET_VALUE);
    }

    private void updatePlayingTrack(Track track) {
        mTextTitle.setText(track.getTitle());
        mTextArtist.setText(track.getUsername());
        Glide.with(this)
                .load(track.getArtworkUrl())
                .into(mImageArtwork);
    }

}
