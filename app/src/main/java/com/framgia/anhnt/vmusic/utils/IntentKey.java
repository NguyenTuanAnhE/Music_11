package com.framgia.anhnt.vmusic.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static com.framgia.anhnt.vmusic.utils.IntentKey.PARCELABLE_LIST;
import static com.framgia.anhnt.vmusic.utils.IntentKey.SELECTED_POSITION;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({
        PARCELABLE_LIST,
        SELECTED_POSITION,
})

public @interface IntentKey {
    String PARCELABLE_LIST = "tracks";
    String SELECTED_POSITION = "position";

}