package com.github.fo2rist.mclaren.mvp;

import android.support.annotation.NonNull;

public interface BasePresenter<T extends BaseView> {
    void onStart(@NonNull T view);

    void onStop();
}
