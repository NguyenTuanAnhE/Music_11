package com.framgia.anhnt.vmusic.screen.home;

import com.framgia.anhnt.vmusic.BasePresenter;
import com.framgia.anhnt.vmusic.BaseView;
import com.framgia.anhnt.vmusic.data.model.Genre;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {
        void showListGenre(List<Genre> genres);
    }

    interface Presenter extends BasePresenter<View> {
        void getListGenre();
    }
}
