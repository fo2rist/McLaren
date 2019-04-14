package com.github.fo2rist.mclaren.mvp

/** Common interface for MVP views. */
interface BaseView

/** Common interface for MVP presenters to used keep initialization consistent. */
interface BasePresenter<T : BaseView> {
    val view: T
}

/** Extension interface for presenters that need to provide de-initialization entry point. */
interface Stoppable {

    /** Should be called when the view is going to die for good and no more interaction expected. */
    fun onStop()
}
