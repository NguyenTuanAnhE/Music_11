package com.framgia.anhnt.vmusic;

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
