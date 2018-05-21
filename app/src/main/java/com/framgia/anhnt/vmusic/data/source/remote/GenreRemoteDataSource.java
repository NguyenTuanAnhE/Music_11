package com.framgia.anhnt.vmusic.data.source.remote;

import com.framgia.anhnt.vmusic.data.model.Genre;
import com.framgia.anhnt.vmusic.data.source.GenreDataSource;
import com.framgia.anhnt.vmusic.data.source.local.GenreLocalDataSource;

import java.util.List;

public class GenreRemoteDataSource implements GenreDataSource.RemoteDataSource {
    private static GenreRemoteDataSource sGenreRemoteDataSource;

    public static GenreRemoteDataSource getInstance() {
        if (sGenreRemoteDataSource == null) {
            sGenreRemoteDataSource = new GenreRemoteDataSource();
        }
        return sGenreRemoteDataSource;
    }

    @Override
    public void getRemoteGenres(GenreDataSource.OnFetchDataListener<Genre> listener) {

    }
}
