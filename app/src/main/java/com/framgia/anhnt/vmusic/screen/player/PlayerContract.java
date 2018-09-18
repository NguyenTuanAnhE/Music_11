package com.framgia.anhnt.vmusic.screen.player;

import com.framgia.anhnt.vmusic.BasePresenter;
import com.framgia.anhnt.vmusic.BaseView;
import com.framgia.anhnt.vmusic.data.model.Track;

import java.util.List;

public interface PlayerContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

    }
}
