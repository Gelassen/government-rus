package ru.home.government.util

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.home.government.App
import ru.home.government.BuildConfig
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.screens.deputies.DeputiesModel
import ru.home.government.screens.deputies.Model
import ru.home.government.screens.laws.BillsModel

inline fun <T> wrapIdlingResource(task: () -> T): T {
    try {
        if (BuildConfig.DEBUG) {
            NetworkIdlingResource.increment()
        }
        val result = task()
        return result
    } catch (ex: Exception) {
        // no really usable because exception block is called after espresso timeout threshold has been passed
        Log.e(App.TAG, "wrapping idling resource exception has been got and rethrown", ex)
        throw ex
    } finally {
        if (BuildConfig.DEBUG) {
            Log.d(App.TAG, "wrapping idling resource is decremented")
            NetworkIdlingResource.decrement()
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Model> T.withNewErrors(newErrors: List<String>): T = when(this) {
    is DeputiesModel -> this.copy(errors = newErrors)
    is BillsModel -> this.copy(errors = newErrors)
    else -> { throw IllegalStateException("${this::class.simpleName} hasn't supported yet. Did you forget to extend Model.withNewErrors()?")}
} as T


fun <T : Model> MutableStateFlow<T>.removeShownError() {
    update { state ->
        state.withNewErrors(newErrors = state.errors.filter { str ->
            !str.equals(state.errors.first()) })
    }
}