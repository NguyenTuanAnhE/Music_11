package com.framgia.anhnt.vmusic.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static com.framgia.anhnt.vmusic.utils.GenreType.ARGUMENT_GENRE;
import static com.framgia.anhnt.vmusic.utils.GenreType.TAB_ALL_AUDIO;
import static com.framgia.anhnt.vmusic.utils.GenreType.TAB_ALL_MUSIC;
import static com.framgia.anhnt.vmusic.utils.GenreType.TAB_ALTER_NATIVE_ROCK;
import static com.framgia.anhnt.vmusic.utils.GenreType.TAB_AMBIENT;
import static com.framgia.anhnt.vmusic.utils.GenreType.TAB_CLASSIC;
import static com.framgia.anhnt.vmusic.utils.GenreType.TAB_COUNTRY;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({
        TAB_ALL_MUSIC,
        TAB_ALL_AUDIO,
        TAB_CLASSIC,
        TAB_AMBIENT,
        TAB_COUNTRY,
        TAB_ALTER_NATIVE_ROCK,
        ARGUMENT_GENRE
})

public @interface GenreType {
    String MY_MUSIC = "My Music";
    String TAB_ALL_MUSIC = "all-music";
    String TAB_ALL_AUDIO = "all-audio";
    String TAB_CLASSIC = "classical";
    String TAB_AMBIENT = "ambient";
    String TAB_COUNTRY = "country";
    String TAB_ALTER_NATIVE_ROCK = "AlterNativeRock";
    String ARGUMENT_GENRE = "argument genre";
}

