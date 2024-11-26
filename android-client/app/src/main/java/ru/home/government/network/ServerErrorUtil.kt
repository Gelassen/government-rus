package ru.home.government.network

import android.content.Context
import ru.home.government.R

class ServerErrorUtil(val context: Context) {

    fun getErrorMessageByServerResponseCode(code: Int): String {
        var result: String = context.getString(R.string.backend_error)
        when(code) {
            304 -> { result += context.getString(R.string.not_modified_error) }
            400 -> { result = context.getString(R.string.bad_request_error) }
            401 -> { result = context.getString(R.string.unauthorized_error) }
            403 -> { result = context.getString(R.string.forbidden_error) }
            404 -> { result = context.getString(R.string.not_found_error) }
            405 -> { result = context.getString(R.string.method_not_allowed_error) }
            409 -> { result = context.getString(R.string.conflict_error) }
            500 -> { result = context.getString(R.string.server_error_error) }
            else -> { result = "${context.getString(R.string.unknown_error)}:${code}" }
        }
        return result
    }

}