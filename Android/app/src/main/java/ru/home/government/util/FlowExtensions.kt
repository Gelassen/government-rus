package ru.home.government.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.home.government.BuildConfig
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.repository.Response

suspend fun <T> Flow<T>.collectSafely(onData: (data: Response.Data<*>) -> Unit, onError: (data: Response.Error) -> Unit) {
    this.collect { it ->
        when(it) {
            is Response.Data<*> -> { onData(it)}
            is Response.Error -> { onError(it) }
        }
    }
}

fun <T> Flow<T>.attachIdlingResource(): Flow<T> {
    this
        .onStart {
            if (BuildConfig.DEBUG) {
                NetworkIdlingResource.increment()
            }
        }
        .onEach {
            if (BuildConfig.DEBUG) {
                NetworkIdlingResource.decrement()
            }

        }
    // TODO consider this option
    /*            .onStart {
                if (BuildConfig.DEBUG) {
                    NetworkIdlingResource.increment()
                }
            }
            .onCompletion {
                if (BuildConfig.DEBUG) {
                    NetworkIdlingResource.decrement()
                }
            }*/
    return this
}