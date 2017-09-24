package com.github.fo2rist.mclaren.mvp;

public interface BasePresenter<T extends BaseView> {
    void onStart(T view);

    void onStop();
}
