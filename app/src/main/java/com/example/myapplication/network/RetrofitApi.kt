package com.example.myapplication.network

import com.example.myapplication.data.DietResponse
import com.example.myapplication.data.LoginResponse
import com.example.myapplication.data.PostJoinRequestDto
import com.example.myapplication.data.PostJoinResponseDto
import com.example.myapplication.data.PostLoginRequestDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// controller 전부 다 넣기
interface RetrofitApi {
    @POST("join")
    suspend fun join(@Body postJoinRequestDto: PostJoinRequestDto): PostJoinResponseDto

    @POST("login")
    suspend fun login(@Body postLoginRequestDto: PostLoginRequestDto): LoginResponse

    @GET("diet")
    suspend fun getDietList(): DietResponse

    @Multipart
    @POST("menu/save")
    suspend fun saveMenu(
        @Part("menuId") menuId: RequestBody,
        @Part("storeId") storeId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("calorie") calorie: RequestBody,
        @Part("carbs") carbs: RequestBody,
        @Part("protein") protein: RequestBody,
        @Part("fat") fat: RequestBody,
        @Part("isSoldOut") isSoldOut: RequestBody,
        @Part menuImg: MultipartBody.Part?
    ): Response
}


/*
    @GET("items")
    suspend fun getItems(): List<Item>

    @POST("items")
    suspend fun createItem(@Body item: Item): Item

    @PUT("items/{id}")
    suspend fun updateItem(@Path("id") id: Int, @Body item: Item): Item

    @DELETE("items/{id}")
    suspend fun deleteItem(@Path("id") id: Int): Void
 */