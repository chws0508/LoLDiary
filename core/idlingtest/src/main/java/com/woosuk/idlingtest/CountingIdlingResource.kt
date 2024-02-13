package com.woosuk.idlingtest

abstract class CountingIdlingResource {
    val countingIdlingResource = SimpleCountingIdlingResource()

    abstract fun increment()

    abstract fun decrement()
}
