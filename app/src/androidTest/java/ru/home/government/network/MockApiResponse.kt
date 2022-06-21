package ru.home.government.network

import android.content.Context
import android.util.Log
import okhttp3.mockwebserver.MockResponse
import ru.home.government.App

object MockApiResponse {

    private fun debugAssets(context: Context) {
        var content = context.assets.list("/")
        Log.d(App.TAG, "Content of assets at '/' ${content}")
    }

    private fun getMessage(context: Context, jsonName: String): String {
//        debugAssets(context)
        return context.assets.open(jsonName).bufferedReader().use { it.readText() }
    }

    object DeputiesApi {
        private lateinit var response: MockResponse

        fun getResponse(): MockResponse {
            return response
        }

        fun setOkDeputiesResponse(context: Context) {
            response = MockResponse()
                .setResponseCode(200)
                .setBody(getMessage(context, "mocks/mock_api_deputies.json"))
        }

        fun setServerErrorResponse() {}

        fun setOkWithNoDeputiesResponse() {}
    }

    object BillsApi {
        fun setOkBillsResponse() {}
        fun setServerErrorResponse() {}
        fun setOkWithBillsResponse() {}
    }
}