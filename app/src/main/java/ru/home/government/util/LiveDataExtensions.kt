package ru.home.government.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dropbox.android.external.store4.FetcherResult
import com.flatstack.android.model.entities.Resource
import com.flatstack.android.model.entities.Status

fun <T : Any> LiveData<Resource<T>>.observeBy(
    owner: LifecycleOwner,
    onNext: (T) -> Unit = {},
    onError: (String) -> Unit = {},
    onLoading: (Boolean) -> Unit = {},
    onSuccess: () -> Unit = {}
) = observe(owner, Observer {
    when (it.status) {
        Status.LOADING -> onLoading(true)
        Status.SUCCESS -> {
            onLoading(false)
            onSuccess()
        }
        Status.ERROR -> {
            onLoading(false)
            it.error?.let(onError)
        }
    }
    it.data?.let(onNext)
})

fun <T : Any> LiveData<FetcherResult<T>>.newObserveBy(
    owner: LifecycleOwner,
    onNext: (T) -> Unit = {},
    onError: (String) -> Unit = {},
    onLoading: (Boolean) -> Unit = {},
    onSuccess: () -> Unit = {}
) = observe(owner, Observer {
    when (it) {
//        Status.LOADING -> onLoading(true)
        is FetcherResult.Data -> {
            onLoading(false)
            onSuccess()
            onNext(it.value)
        }
        is FetcherResult.Error.Exception -> {
            onLoading(false)
            onError(it.error.toString())
        }
    }
//    onNext(it)
//    it.data?.let(onNext)
})

fun <T : Any> T.toLiveData() = object : LiveData<T>() {
    init {
        postValue(this@toLiveData)
    }
}
