package com.woosuk.loldiary.ui.utils

class ProductIdlingResource : CountingIdlingResource() {
    override fun increment() = Unit

    override fun decrement() = Unit
}
