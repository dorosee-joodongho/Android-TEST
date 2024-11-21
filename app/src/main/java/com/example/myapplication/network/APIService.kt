package com.example.myapplication.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface APIService {

    // GET 요청 (헤더에 Banner 토큰을 추가)
    @GET
    suspend fun <T> getApi(
        @Url endpoint: String,
        @Header("Authorization") bannerToken: String?,  // Banner토큰을 Authorization 헤더로 전달
        @Query("param") param: String? = null
    ): T

    // POST 요청 (헤더에 Banner 토큰을 추가)
    @POST
    suspend fun <T> postApi(
        @Url endpoint: String,
        @Header("Authorization") bannerToken: String?,  // Banner토큰을 Authorization 헤더로 전달
        @Body body: Any
    ): T
}
