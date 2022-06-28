package ru.home.government.util

import ru.home.government.BuildConfig
import ru.home.government.di.test.NetworkIdlingResource

inline fun <T> wrapIdlingResource(task: () -> T): T {
    if (BuildConfig.DEBUG) {
        NetworkIdlingResource.increment()
    }
    val result = task()
    if (BuildConfig.DEBUG) {
        NetworkIdlingResource.decrement()
    }
    return result
}