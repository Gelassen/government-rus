package ru.home.government.di.fakes

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import ru.home.government.stubs.Stubs
import ru.home.government.model.GovResponse
import ru.home.government.network.IApi
import ru.home.government.repository.pagination.BillsPagingSource

class FakeBillPagingSource(api: IApi, apiKey: String, apiAppToken: String)
    : BillsPagingSource(api, apiKey, apiAppToken) {

    private lateinit var apiResponse: Response<GovResponse>

    fun setOkWithFullPayloadResponse() {
        apiResponse = preparePositiveResponse()
    }

    fun setErrorResponse() {
        apiResponse = Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "{}"))
    }

    fun setOkWithEmptyPayload() {
        apiResponse = preparePositiveResponseWithEmptyPayload()
    }

    override suspend fun execute(page: Int): Response<GovResponse> {
        return apiResponse
    }

    // for tests only; evenmore, remove it when you will be able to provide full API stub
    override val keyReuseSupported: Boolean
        get() = true

    private fun preparePositiveResponse(): Response<GovResponse> {
        val gson = Gson()
        val json = Stubs.ApiResponse.lawsOkV2ApiRes
        val body = gson.fromJson<GovResponse>(json, GovResponse::class.java)
        return Response.success(body)
    }

    private fun preparePositiveResponseWithEmptyPayload(): Response<GovResponse> {
        val body = GovResponse()
        body.count = 0
        body.page = 2
        body.laws = emptyList()
        body.wording = ""
        return Response.success(body)
    }
}