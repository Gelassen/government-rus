package ru.home.government.di.test

import androidx.test.espresso.idling.CountingIdlingResource


object NetworkIdlingResource {

    private const val NETWORK = "NETWORK"

    @JvmField val countingIdlingResource = CountingIdlingResource(NETWORK)

    fun increment() {
        countingIdlingResource.increment()
        countingIdlingResource.dumpStateToLogs()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
            countingIdlingResource.dumpStateToLogs()
        }
    }
}