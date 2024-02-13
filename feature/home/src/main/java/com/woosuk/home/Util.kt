package com.woosuk.home

fun Long.toGameDurationFormat(): String {
    val minutes = this / 100
    val seconds = this - (minutes * 100)

    return "$minutes:$seconds"
}
