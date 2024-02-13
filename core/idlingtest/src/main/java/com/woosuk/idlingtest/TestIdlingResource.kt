package com.woosuk.idlingtest

class TestIdlingResource : CountingIdlingResource() {
    override fun increment() {
        countingIdlingResource.increment()
    }

    override fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}
