package ru.home.government

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.home.government.model.Deputy

interface Api {

    @GET("/api/{token}/deputies.json")
    fun getAllDeputies(@Path("token") token: String,  @Query("app_token") appToken: String): Observable<List<Deputy>>
}