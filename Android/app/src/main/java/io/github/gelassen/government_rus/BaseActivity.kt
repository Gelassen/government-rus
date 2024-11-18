package io.github.gelassen.government_rus

import android.os.Build
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    @Suppress("DEPRECATION")
    protected fun getApiSupportColor(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            resources.getColor(R.color.colorActionBar, theme)
        else
            resources.getColor(R.color.colorActionBar)
    }
}