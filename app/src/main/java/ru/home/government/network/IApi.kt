package ru.home.government.network

import com.flatstack.android.model.network.ApiResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse

interface IApi {

    // http://api.duma.gov.ru/api/<api_key>/search.json?status=1&app_token=<app_token>
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/search.json?status=1")
    fun getIntroducedLaws(
        @Path("token") token: String,
        @Query("app_token") appToken: String
    ): Deferred<ApiResponse<GovResponse>>

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/deputies.json")
    fun getAllDeputies(
        @Path("token") token: String,
        @Query("app_token") appToken: String
    ): Deferred<ApiResponse<List<Deputy>>>
}