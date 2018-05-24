package com.framgia.anhnt.vmusic.screen.player;

public class PlayerPresenter implements PlayerContract.Presenter {
    private PlayerContract.View mView;

    @Override
    public void setView(PlayerContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
