package ru.home.government.screens

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    protected fun visibleProgress(show: Boolean) {
//        refreshLayout.isRefreshing = show
    }

    protected fun showError(errorText: String?) {
        errorText?.let {
            Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
        }
    }
}