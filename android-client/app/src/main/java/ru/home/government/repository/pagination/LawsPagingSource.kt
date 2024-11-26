package ru.home.government.repository.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import ru.home.government.model.dto.GovResponse
import ru.home.government.model.dto.Law
import ru.home.government.network.IApi
import ru.home.government.network.IApi.Const.PAGE_SIZE
import ru.home.government.network.ServerErrorUtil
import ru.home.government.util.wrapIdlingResource
import java.io.IOException
import java.lang.IllegalStateException

const val DEFAULT_START_PAGE = 1

open class BillsPagingSource(
    private val api: IApi,
    private val errorMessageUtil: ServerErrorUtil,
    private val apiKey: String,
    private val apiAppToken: String
)
    : PagingSource<Int, Law>() {

    override fun getRefreshKey(state: PagingState<Int, Law>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Law> {
        try {
            val page = params.key ?: DEFAULT_START_PAGE

            val response = wrapIdlingResource { execute(page) }
            if (response.isSuccessful) {
                val govResponse = response.body()
                return LoadResult.Page(
                    data = govResponse!!.laws,
                    prevKey = if (page == DEFAULT_START_PAGE) null else  page.minus(1),
                    nextKey = if (govResponse.laws?.isEmpty()!!)  null else govResponse.page.plus(params.loadSize / PAGE_SIZE)
                )
            } else {
                val msg = errorMessageUtil.getErrorMessageByServerResponseCode(response.code())
                return LoadResult.Error(IllegalStateException(msg))
            }
        } catch (ex: IOException) {
            return LoadResult.Error(ex)
        } catch (ex: HttpException) {
            return LoadResult.Error(ex)
        }
    }

    protected open suspend fun execute(page: Int): Response<GovResponse> {
        return api.getIntroducedLaws(apiKey, apiAppToken, page)
    }
}