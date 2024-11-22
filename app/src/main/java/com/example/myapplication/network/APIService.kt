package com.example.myapplication.network

import retrofit2.http.*

interface APIService {

    // GET 요청 (헤더에 Banner 토큰을 추가)
    @GET
    suspend fun <T> getApi(
        @Url endpoint: String,
        @Header("Authorization") bannerToken: String?,  // Banner 토큰
        @Query("param") param: String? = null
    ): T

    // POST 요청 (헤더에 Banner 토큰을 추가)
    @POST
    suspend fun <T> postApi(
        @Url endpoint: String,
        @Header("Authorization") bannerToken: String?,  // Banner 토큰
        @Body body: Any
    ): T

    // PUT 요청 (헤더에 Banner 토큰을 추가)
    @PUT
    suspend fun <T> putApi(
        @Url endpoint: String,
        @Header("Authorization") bannerToken: String?,  // Banner 토큰
        @Body body: Any
    ): T

    // DELETE 요청 (헤더에 Banner 토큰을 추가)
    @DELETE
    suspend fun <T> deleteApi(
        @Url endpoint: String,
        @Header("Authorization") bannerToken: String?  // Banner 토큰
    ): T
}
