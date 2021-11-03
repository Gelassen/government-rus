package ru.home.government.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import ru.home.government.BuildConfig
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.model.VotesResponse
import ru.home.government.repository.Response

fun <T> Flow<T>.attachIdlingResource(): Flow<T> {
    this
        .onStart {
            if (BuildConfig.DEBUG) {
                NetworkIdlingResource.increment()
            }
        }
        .onCompletion {
            if (BuildConfig.DEBUG) {
                NetworkIdlingResource.decrement()
            }

        }
    return this
}