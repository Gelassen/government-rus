package ru.home.government.network

import com.dropbox.android.external.store4.FetcherResult
import com.flatstack.android.model.network.ApiResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse
import ru.home.government.model.VotesResponse

interface IApi {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json?sort=last_event_date&limit=20")
    suspend fun newGetIntroducedLaws(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("page") page: Int
    ): FetcherResult<GovResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json")
    fun newGetLawByNumber(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("number") number: String
    ): FetcherResult<GovResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json")
    fun newGetLawByName(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("page") page: Int,
        @Query("name") name: String
    ): FetcherResult<GovResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/voteSearch.json")
    fun newGetLawVotes(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("number") number: String
    ): FetcherResult<VotesResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/deputies.json")
    suspend fun newGetAllDeputies(
        @Path("token") token: String,
        @Query("app_token") appToken: String
    ): FetcherResult<List<Deputy>>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/deputies.json")
    suspend fun testGetAllDeputies(
        @Path("token") token: String,
        @Query("app_token") appToken: String
    ): FetcherResult<List<Deputy>>

    // http://api.duma.gov.ru/api/<api_key>/search.json?status=1&app_token=<app_token>
    @Deprecated(message = "migrate on new network layer")
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json?sort=last_event_date&limit=20")
    fun getIntroducedLaws(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("page") page: Int
    ): Deferred<ApiResponse<GovResponse>>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json")
    fun getLawByNumber(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("number") number: String
    ): Deferred<ApiResponse<GovResponse>>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json")
    fun getLawByName(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("page") page: Int,
        @Query("name") name: String
    ): Deferred<ApiResponse<GovResponse>>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/voteSearch.json")
    fun getLawVotes(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("number") number: String
    ): Deferred<ApiResponse<VotesResponse>>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/deputies.json")
    fun getAllDeputies(
        @Path("token") token: String,
        @Query("app_token") appToken: String
    ): Deferred<ApiResponse<List<Deputy>>>
}