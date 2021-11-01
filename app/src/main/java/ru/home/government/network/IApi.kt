package ru.home.government.network

import com.dropbox.android.external.store4.FetcherResult
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
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
    @GET("/api/{token}/search.json?sort=last_event_date&limit=20")
    suspend fun newGetIntroducedLaws(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("page") page: Int
    ): FetcherResult<GovResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json")
    suspend fun newGetLawByNumber(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("number") number: String
    ): FetcherResult<GovResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json")
    suspend fun newGetLawByName(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("page") page: Int,
        @Query("name") name: String
    ): FetcherResult<GovResponse>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/voteSearch.json")
    suspend fun newGetLawVotes(
        @Path("token") token: String,
        @Query("app_token") appToken: String,
        @Query("number") number: String
    ): FetcherResult<VotesResponse>

    @Deprecated(message = "Use getAllDeputiesV2() instead")
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/deputies.json")
    suspend fun newGetAllDeputies(
        @Path("token") token: String,
        @Query("app_token") appToken: String
    ): FetcherResult<List<Deputy>>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/deputies.json")
    suspend fun getAllDeputiesV2(
        @Path("token") token: String,
        @Query("app_token") appToken: String
    ): Response<List<Deputy>>

}