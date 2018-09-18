package com.framgia.anhnt.vmusic.data.source.local;

import android.content.Context;
import android.util.Log;

import com.framgia.anhnt.vmusic.data.iterator.GetGenreLocalIterator;
import com.framgia.anhnt.vmusic.data.model.Genre;
import com.framgia.anhnt.vmusic.data.source.GenreDataSource;

public class GenreLocalDataSource implements GenreDataSource.LocalDataSource {
    private static GenreLocalDataSource sGenreLocalDataSource;
    private GetGenreLocalIterator mGetGenreLocalIterator;

    public static GenreLocalDataSource getInstance() {
        return sGenreLocalDataSource;
    }

    public static void initContext(Context context) {
        if (sGenreLocalDataSource == null) {
            sGenreLocalDataSource = new GenreLocalDataSource(context);
        }
    }

    private GenreLocalDataSource(Context context) {
        mGetGenreLocalIterator = GetGenreLocalIterator.getInstance(context);
    }

    @Override
    public void getLocalGenres(GenreDataSource.OnFetchDataListener<Genre> listener) {
        mGetGenreLocalIterator.getGenre(listener);
    }
}
