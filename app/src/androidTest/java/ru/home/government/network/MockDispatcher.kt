package ru.home.government.network

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockDispatcher : Dispatcher() {

    private val apiResponse: MockApiResponse = MockApiResponse

    override fun dispatch(request: RecordedRequest?): MockResponse {
        lateinit var response: MockResponse
        if (request!!.path.equals("/deputies")) {
            response = MockApiResponse.DeputiesApi.getResponse()
        } else {
            throw IllegalStateException("There is not mocked API response for this request. Did you forget add mock implementation into dispatcher")
        }
        return response
    }

    fun getApiResponse() : MockApiResponse {
        return apiResponse
    }
}