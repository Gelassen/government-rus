package ru.home.government.repository.pagination

import androidx.paging.PagingSource
import com.dropbox.android.external.store4.FetcherResult
import retrofit2.HttpException
import ru.home.government.model.GovResponse
import ru.home.government.model.Law
import ru.home.government.network.IApi
import java.io.IOException

private const val GOV_STARTING_PAGE_INDEX = 1

class LawsPageSource(
    private val api: IApi,
    private val apiKey: String,
    private val appToken: String
) : PagingSource<Int, Law>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Law> {
        return try {
            val page = params.key ?: GOV_STARTING_PAGE_INDEX
            val response = api.newGetIntroducedLaws(apiKey, appToken, page)
            var data: GovResponse = GovResponse()
            when(response) {
                is FetcherResult.Data -> {
                    data = response.value as GovResponse
                }
                is FetcherResult.Error.Exception -> {
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