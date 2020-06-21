package ru.home.government.repository

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val KEY_LAWS = "KEY_LAWS"

class CacheRepository(val context: Context) {

    var pref: SharedPreferences

    init {
        pref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun saveLawCode(code: String) {
        val values = getLawCodes()
        values.add(code)
        pref.edit()
            .putStringSet(KEY_LAWS, values)
            .apply()
    }

    fun getLawCodes(): MutableSet<String> {
        return pref.getStringSet(KEY_LAWS, mutableSetOf())!!
    }

    fun removeLawCode(lawCode: String) {
        val values = getLawCodes()
        values.remove(lawCode)
        pref.edit()
            .putStringSet(KEY_LAWS, values)
            .apply()
    }

    fun isCodeInCache(lawCode: String): Boolean {
        return getLawCodes().contains(lawCode)
    }
}