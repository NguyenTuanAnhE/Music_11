package com.framgia.anhnt.vmusic.screen.player;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.framgia.anhnt.vmusic.BaseActivity;
import com.framgia.anhnt.vmusic.R;

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

        mPresenter = new PlayerPresenter();
        mPresenter.setView(this);

        mPlayerAdapter = new PlayerAdapter(this);
        mRecyclerPlayer.setAdapter(mPlayerAdapter);
        mRecyclerPlayer.setLayoutManager(new LinearLayoutManager(this));
    }
}
