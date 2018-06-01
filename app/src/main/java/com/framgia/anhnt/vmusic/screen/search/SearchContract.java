package com.framgia.anhnt.vmusic.screen.search;

import com.framgia.anhnt.vmusic.BasePresenter;
import com.framgia.anhnt.vmusic.BaseView;
import com.framgia.anhnt.vmusic.data.model.Track;

import java.util.List;

public interface SearchContract {
    interface View extends BaseView {
        void showListTrack(List<Track> tracks);

        void showFailMessage();
    }

    interface Presenter extends BasePresenter<SearchContract.View> {
        void searchTrack(int limit,int offset, String key);
    }
}
