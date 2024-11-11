package ru.home.government.screens

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import ru.home.government.R

abstract class BaseActivity: AppCompatActivity() {

    @Suppress("DEPRECATION")
    protected fun getApiSupportColor(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            resources.getColor(R.color.colorActionBar, theme)
        else
            resources.getColor(R.color.colorActionBar)
    }
}