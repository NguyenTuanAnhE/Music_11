package com.framgia.anhnt.vmusic.utils;

import android.app.Application;

import com.framgia.anhnt.vmusic.data.source.local.GenreLocalDataSource;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GenreLocalDataSource.initContext(this);
    }
}
