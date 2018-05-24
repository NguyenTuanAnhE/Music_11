package com.framgia.anhnt.vmusic.screen.online;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.anhnt.vmusic.screen.online.genre.GenreFragment;
import com.framgia.anhnt.vmusic.utils.Constants;
import com.framgia.anhnt.vmusic.utils.GenreType;
import com.framgia.anhnt.vmusic.utils.TabPosition;

public class OnlinePagerAdapter extends FragmentPagerAdapter {
    public OnlinePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TabPosition.TAB_ALL_MUSIC:
                return GenreFragment.newInstance(GenreType.TAB_ALL_MUSIC);
            case TabPosition.TAB_ALL_AUDIO:
                return GenreFragment.newInstance(GenreType.TAB_ALL_AUDIO);
            case TabPosition.TAB_CLASSIC:
                return GenreFragment.newInstance(GenreType.TAB_CLASSIC);
            case TabPosition.TAB_AMBIENT:
                return GenreFragment.newInstance(GenreType.TAB_AMBIENT);
            case TabPosition.TAB_COUNTRY:
                return GenreFragment.newInstance(GenreType.TAB_COUNTRY);
            case TabPosition.TAB_ALTER_NATIVE_ROCK:
                return GenreFragment.newInstance(GenreType.TAB_ALTER_NATIVE_ROCK);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TabPosition.TAB_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TabPosition.TAB_ALL_MUSIC:
                return GenreType.TAB_ALL_MUSIC;
            case TabPosition.TAB_ALL_AUDIO:
                return GenreType.TAB_ALL_AUDIO;
            case TabPosition.TAB_CLASSIC:
                return GenreType.TAB_CLASSIC;
            case TabPosition.TAB_AMBIENT:
                return GenreType.TAB_AMBIENT;
            case TabPosition.TAB_COUNTRY:
                return GenreType.TAB_COUNTRY;
            case TabPosition.TAB_ALTER_NATIVE_ROCK:
                return GenreType.TAB_ALTER_NATIVE_ROCK;
            default:
                return null;
        }
    }
}
