package com.example.myapplication.network

import android.telecom.Call
import com.example.myapplication.data.LoginResponse
import com.example.myapplication.data.PostJoinRequestDto
import com.example.myapplication.data.PostJoinResponseDto
import com.example.myapplication.data.PostLoginRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

// controller 전부 다 넣기
interface RetrofitApi {
    @POST("join")
    suspend fun join(@Body postJoinRequestDto: PostJoinRequestDto): PostJoinResponseDto

    @POST("login")
    suspend fun login(@Body postLoginRequestDto: PostLoginRequestDto): LoginResponse
}


/*
    @GET("items")
    fun getItems(): Call<List<Item>>

    @POST("items")
    fun createItem(@Body item: Item): Call<Item>

    @PUT("items/{id}")
    fun updateItem(@Path("id") id: Int, @Body item: Item): Call<Item>

    @DELETE("items/{id}")
    fun deleteItem(@Path("id") id: Int): Call<Void>
 */