package ru.home.government.network

import android.content.Context
import android.util.Log
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import ru.home.government.App

class MockDispatcher(val context: Context) : Dispatcher() {

    object RequestUri {
        const val DEPUTIES = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/deputies.json?app_token=appa409190224f66043f299efe0d5647f7a5adb2039 HTTP/1.1"
        const val BILLS_MAIN_PAGE_1 = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/search.json?sort=last_event_date&limit=20&app_token=appa409190224f66043f299efe0d5647f7a5adb2039&page=1 HTTP/1.1"
        const val BILLS_MAIN_PAGE_2 = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/search.json?sort=last_event_date&limit=20&app_token=appa409190224f66043f299efe0d5647f7a5adb2039&page=2 HTTP/1.1"
        const val BILL_SPECIFIC_149922_8 = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/search.json?app_token=appa409190224f66043f299efe0d5647f7a5adb2039&number=149922-8 HTTP/1.1"
        const val BILL_149922_8_VOTES = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/voteSearch.json?app_token=appa409190224f66043f299efe0d5647f7a5adb2039&number=149922-8 HTTP/1.1"
        /* not full bill response */
        const val BILL_SPECIFIC_149604_8  = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/search.json?app_token=appa409190224f66043f299efe0d5647f7a5adb2039&number=149604-8 HTTP/1.1"
        const val BILL_149604_8_VOTES = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/voteSearch.json?app_token=appa409190224f66043f299efe0d5647f7a5adb2039&number=149604-8 HTTP/1.1"

        const val BILLS_SEARCH_PAGE_1 = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/search.json?app_token=appa409190224f66043f299efe0d5647f7a5adb2039&page=1&name=%D1%81%D1%83%D0%B4 HTTP/1.1"
        const val BILLS_SEARCH_PAGE_2 = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/search.json?app_token=appa409190224f66043f299efe0d5647f7a5adb2039&page=2&name=%D1%81%D1%83%D0%B4 HTTP/1.1"
        const val BILLS_SEARCH_EMPTY  = "GET /api/35b04d9a9344a1befc4c95f519b279a9a738cf28/search.json?app_token=appa409190224f66043f299efe0d5647f7a5adb2039&page=1&name=med HTTP/1.1"
    }

    private val apiResponse: MockApiResponse = MockApiResponse(context)

    fun getApiResponse() : MockApiResponse {
        return apiResponse
    }

    override fun dispatch(request: RecordedRequest): MockResponse {
        lateinit var response: MockResponse
        Log.d(App.TAG, "Request line: ${request.requestLine}")
        val requestLine = request.requestLine
        when (requestLine) {
            RequestUri.DEPUTIES -> { response = apiResponse.deputiesApi.getResponse() }
            RequestUri.BILLS_MAIN_PAGE_1 -> { response = apiResponse.billsApi.getResponse() }
            RequestUri.BILLS_MAIN_PAGE_2 -> { response = apiResponse.billsApi.get2ndPageResponse() }
            RequestUri.BILL_SPECIFIC_149922_8 -> { response = apiResponse.billSpecificApi.getResponse() }
            RequestUri.BILL_149922_8_VOTES -> { response = apiResponse.billSpecificVotesApi.getResponse() }
            RequestUri.BILL_SPECIFIC_149604_8 -> { response = apiResponse.billSpecificApi.getResponse() }
            RequestUri.BILL_149604_8_VOTES -> { response = apiResponse.billSpecificVotesApi.getResponse() }
            RequestUri.BILLS_SEARCH_PAGE_1 -> { response = apiResponse.billsSearchApi.getResponse() }
            RequestUri.BILLS_SEARCH_PAGE_2 -> { response = apiResponse.billsSearchApi.get2ndPageResponse() }
            RequestUri.BILLS_SEARCH_EMPTY -> { response = apiResponse.billsSearchApi.getResponse() }
            else -> {
                throw IllegalStateException("There is not mocked API response for this request. " +
                    "Did you forget add mock implementation into dispatcher? " +
                    "([request] ${request.requestLine})")
            }
        }
        return response
    }
}