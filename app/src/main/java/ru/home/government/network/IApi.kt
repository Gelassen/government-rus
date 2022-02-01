package ru.home.government.network

import com.dropbox.android.external.store4.FetcherResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse
import ru.home.government.model.VotesResponse

interface IApi {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json?sort=last_event_date&limit=${Const.PAGE_SIZE}")
    suspend fun getIntroducedLaws(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("page") page: Int
    ): Response<GovResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json")
    suspend fun getLawByNameV2(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("page") page: Int,
        @Query("name") name: String
    ): Response<GovResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json")
    suspend fun getLawByNumberV2(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("number") number: String
    ): Response<GovResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/deputies.json")
    suspend fun getAllDeputiesV2(
        @Path("token") token: String,
        @Query("app_token") appToken: String
    ): Response<List<Deputy>>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/voteSearch.json")
    suspend fun newGetLawVotesV2(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("number") number: String
    ): Response<VotesResponse>

    object Const {
        const val PAGE_SIZE = 20
    }

}