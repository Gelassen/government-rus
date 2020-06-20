package ru.home.government.screens

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    protected var progressView: View? = null

    protected fun visibleProgress(show: Boolean) {
        if (progressView == null) return
        Log.d("DEPUTIES", "visibleProgress: " + show)
        progressView!!.visibility = if (show) View.VISIBLE else View.GONE
    }

    protected fun showError(errorText: String?) {
        errorText?.let {
            Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
        }
    }
}