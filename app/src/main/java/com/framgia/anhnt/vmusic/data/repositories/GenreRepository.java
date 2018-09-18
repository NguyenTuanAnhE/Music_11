package com.framgia.anhnt.vmusic.data.repositories;

import com.framgia.anhnt.vmusic.data.model.Genre;
import com.framgia.anhnt.vmusic.data.source.GenreDataSource;

public class GenreRepository implements GenreDataSource.RemoteDataSource,
        GenreDataSource.LocalDataSource {
    private static GenreRepository sGenreRepository;
    private GenreDataSource.LocalDataSource mLocal;
    private GenreDataSource.RemoteDataSource mRemote;

    public static GenreRepository getInstance(GenreDataSource.LocalDataSource local,
                                              GenreDataSource.RemoteDataSource remote) {
        if (sGenreRepository == null) {
            sGenreRepository = new GenreRepository(local, remote);
        }
        return sGenreRepository;
    }

    private GenreRepository(GenreDataSource.LocalDataSource local,
                            GenreDataSource.RemoteDataSource remote) {
        mLocal = local;
        mRemote = remote;
    }

    @Override
    public void getRemoteGenres(GenreDataSource.OnFetchDataListener<Genre> listener) {
        mRemote.getRemoteGenres(listener);
    }

    @Override
    public void getLocalGenres(GenreDataSource.OnFetchDataListener<Genre> listener) {
        mLocal.getLocalGenres(listener);
    }
}
