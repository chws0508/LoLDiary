package com.woosuk.loldiary.ui.utils

abstract class CountingIdlingResource {
    val countingIdlingResource = SimpleCountingIdlingResource()

    abstract fun increment()

    abstract fun decrement()
}
