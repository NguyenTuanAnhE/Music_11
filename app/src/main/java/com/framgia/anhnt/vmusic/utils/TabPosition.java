package com.framgia.anhnt.vmusic.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.framgia.anhnt.vmusic.utils.TabPosition.TAB_ALL_AUDIO;
import static com.framgia.anhnt.vmusic.utils.TabPosition.TAB_ALL_MUSIC;
import static com.framgia.anhnt.vmusic.utils.TabPosition.TAB_ALTER_NATIVE_ROCK;
import static com.framgia.anhnt.vmusic.utils.TabPosition.TAB_AMBIENT;
import static com.framgia.anhnt.vmusic.utils.TabPosition.TAB_CLASSIC;
import static com.framgia.anhnt.vmusic.utils.TabPosition.TAB_COUNT;
import static com.framgia.anhnt.vmusic.utils.TabPosition.TAB_COUNTRY;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({
        TAB_ALL_MUSIC,
        TAB_ALL_AUDIO,
        TAB_CLASSIC,
        TAB_AMBIENT,
        TAB_COUNTRY,
        TAB_ALTER_NATIVE_ROCK,
        TAB_COUNT
})
public @interface TabPosition {
    int TAB_ALL_MUSIC = 0;
    int TAB_ALL_AUDIO = 1;
    int TAB_CLASSIC = 2;
    int TAB_AMBIENT = 3;
    int TAB_COUNTRY = 4;
    int TAB_ALTER_NATIVE_ROCK = 5;
    int TAB_COUNT = 6;
}
