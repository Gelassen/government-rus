package ru.home.government.repository.pagination

import retrofit2.Response
import ru.home.government.model.GovResponse
import ru.home.government.network.IApi

class SearchLawsPagingSource(
    private val api: IApi,
    private val apiKey: String,
    private val apiAppToken: String,
    private val searchStr: String
) : BillsPagingSource(api, apiKey, apiAppToken) {

    override suspend fun execute(page: Int): Response<GovResponse> {
        return api.getLawByNameV2(apiKey, apiAppToken, page, searchStr)
    }
}