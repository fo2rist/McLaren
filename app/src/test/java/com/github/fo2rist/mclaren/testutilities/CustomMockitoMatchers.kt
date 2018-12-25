package com.github.fo2rist.mclaren.testutilities

import org.mockito.Mockito

/**
 * Null-check safe version of [Mockito.any]
 */
fun <T> anyKotlinObject(): T {
    Mockito.any<T>()
    return uninitialized()
}

@Suppress("UNCHECKED_CAST")
private fun <T> uninitialized(): T = null as T
