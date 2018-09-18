package com.framgia.anhnt.vmusic.screen.offline;

import com.framgia.anhnt.vmusic.BasePresenter;
import com.framgia.anhnt.vmusic.BaseView;
import com.framgia.anhnt.vmusic.data.model.Track;

import java.util.List;

public interface OfflineContract {
    interface View extends BaseView {
        void showListTracks(List<Track> tracks);
    }

    interface Presenter extends BasePresenter<OfflineContract.View> {
        void getLocalTracks();
    }
}
