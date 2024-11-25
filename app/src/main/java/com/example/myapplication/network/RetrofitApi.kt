package com.example.myapplication.network

import android.telecom.Call
import com.example.myapplication.data.Diet
import com.example.myapplication.data.DietResponse
import com.example.myapplication.data.LoginResponse
import com.example.myapplication.data.PostJoinRequestDto
import com.example.myapplication.data.PostJoinResponseDto
import com.example.myapplication.data.PostLoginRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// controller 전부 다 넣기
// 단일 객체는 객체로 또 감싸서 Response 받기
interface RetrofitApi {
    @POST("join")
    suspend fun join(@Body postJoinRequestDto: PostJoinRequestDto): PostJoinResponseDto

    @POST("login")
    suspend fun login(@Body postLoginRequestDto: PostLoginRequestDto): LoginResponse

    @GET("diet")
    suspend fun getDietList(): DietResponse
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