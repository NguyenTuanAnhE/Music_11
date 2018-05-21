package com.framgia.anhnt.vmusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.framgia.anhnt.vmusic.utils.ColorUtil;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeEnable());
            getSupportActionBar().setDisplayShowHomeEnabled(showHomeEnable());
        }
        ColorUtil.changeStatusBarColor(this, getStatusBarColor());
        initComponents();
    }

    protected abstract boolean showHomeEnable();

    public abstract int getContentLayout();

    public abstract int getStatusBarColor();

    public abstract void initComponents();

}
