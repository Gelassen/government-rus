package ru.home.government.repository

import androidx.paging.PagingSource
import com.flatstack.android.model.network.ApiErrorResponse
import com.flatstack.android.model.network.ApiSuccessResponse
import retrofit2.HttpException
import ru.home.government.model.GovResponse
import ru.home.government.model.Law
import ru.home.government.network.IApi
import java.io.IOException

private const val GOV_STARTING_PAGE_INDEX = 1

class SearchLawsPageSource(
    private val api: IApi,
    private val apiKey: String,
    private val appToken: String,
    private val searchStr: String
) : PagingSource<Int, Law>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Law> {
        return try {
            val page = params.key ?: GOV_STARTING_PAGE_INDEX
            val response = api.getLawByName(apiKey, appToken, page, searchStr).await()
            var data: GovResponse = GovResponse()
            when(response) {
                is ApiSuccessResponse -> {
                    data = response.body as GovResponse
                }
                is ApiErrorResponse -> {
                    // leave empty response
                }
            }
            LoadResult.Page(
                data = data.laws,
                prevKey = if (page == GOV_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}