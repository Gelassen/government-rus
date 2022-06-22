package ru.home.government.screens

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.home.government.R

open class BaseFragment: Fragment() {

    protected var progressView: View? = null

    protected fun visibleProgress(show: Boolean) {
        if (progressView == null) return
        progressView!!.visibility = if (show) View.VISIBLE else View.GONE
    }

    /**
     * @Deprecated Use {@link #showError(view: View, text: String) showError(view: View, text: String)} instead
     * */
    @Deprecated(message = "Migrate to SnackBar",
        replaceWith = ReplaceWith(
            "showError(view: View, text: String)}",
        )
    )
    protected fun showError(errorText: String?) {
        errorText?.let {
            Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
        }
    }

    protected fun showError(view: View, text: String) {
        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        if (requireActivity().findViewById<View?>(R.id.nav_view) != null) {
            snackbar.view.minimumHeight = requireActivity().findViewById<View?>(R.id.nav_view).height
        }
        snackbar.show()
    }
}