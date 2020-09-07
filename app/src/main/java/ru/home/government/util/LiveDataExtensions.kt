package ru.home.government.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dropbox.android.external.store4.FetcherResult

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
