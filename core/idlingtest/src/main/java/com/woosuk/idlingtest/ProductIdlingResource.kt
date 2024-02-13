package com.woosuk.idlingtest

class ProductIdlingResource : CountingIdlingResource() {
    override fun increment() = Unit

    override fun decrement() = Unit
}
