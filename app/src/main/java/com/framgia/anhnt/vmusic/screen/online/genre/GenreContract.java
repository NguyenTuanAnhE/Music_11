package com.framgia.anhnt.vmusic.screen.online.genre;

import com.framgia.anhnt.vmusic.BasePresenter;
import com.framgia.anhnt.vmusic.BaseView;
import com.framgia.anhnt.vmusic.data.model.Track;

import java.util.List;

public interface GenreContract {
    interface View extends BaseView {
        void showListTrack(List<Track> tracks);

        void getListFail();
    }

    interface Presenter extends BasePresenter<View> {
        void loadTrackByGenre(String genre, int limit, int offset);
    }
}
