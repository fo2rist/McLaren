package com.github.fo2rist.mclaren.utils

/**
 * Maker property that can be used on when statements to ensure they're exhaustive.
 */
inline val <T> T.exhaustive: T
    get() = this
