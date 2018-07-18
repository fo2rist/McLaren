package com.github.fo2rist.mclaren.mvp;

import android.support.annotation.NonNull;

/** Common interface for MVP presenters in the app to used keep start/stop consistent. */
public interface BasePresenter<T extends BaseView> {
    /**
     * Should be called when the view starts and the interaction is possible.
     */
    void onStart(@NonNull T view);

    /**
     * Should be called when the view is going to die for good and no more interaction expected.
     */
    void onStop();
}
