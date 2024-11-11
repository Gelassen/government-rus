package ru.home.government.screens

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.home.government.R

open class BaseFragment: Fragment() {

    protected var progressView: View? = null

    protected open fun visibleProgress(show: Boolean) {
        if (progressView == null) return
        progressView!!.visibility = if (show) View.VISIBLE else View.GONE
    }

    protected fun showError(view: View, text: String) {
        showError(view, text) { }
    }

    protected fun showError(view: View, text: String, onDismiss: () -> Unit) {
        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        if (requireActivity().findViewById<View?>(R.id.nav_view) != null) {
            snackbar.view.minimumHeight = requireActivity().findViewById<View?>(R.id.nav_view).height
        }
        snackbar.addCallback(object: Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                onDismiss()
            }
        }).show()
    }
}