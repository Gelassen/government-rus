package ru.home.government.network

import com.flatstack.android.model.network.ApiResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.home.government.model.Deputy

interface IApi {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/api/{token}/deputies.json")
    fun getAllDeputies(
        @Path("token") token: String,
        @Query("app_token") appToken: String
    ): Deferred<ApiResponse<List<Deputy>>>
}