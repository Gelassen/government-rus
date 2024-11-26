package ru.home.government.repository.pagination

import retrofit2.Response
import ru.home.government.model.dto.GovResponse
import ru.home.government.network.IApi
import ru.home.government.network.ServerErrorUtil

class SearchLawsPagingSource(
    private val api: IApi,
    private val serverErrorUtil: ServerErrorUtil,
    private val apiKey: String,
    private val apiAppToken: String,
    private val searchStr: String
) : BillsPagingSource(api, serverErrorUtil, apiKey, apiAppToken) {

    override suspend fun execute(page: Int): Response<GovResponse> {
        return api.getLawByName(apiKey, apiAppToken, page, searchStr)
    }


}