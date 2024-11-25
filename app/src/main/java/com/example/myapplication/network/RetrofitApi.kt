package com.example.myapplication.network

import android.telecom.Call
import com.example.myapplication.data.PostJoinRequestDto
import com.example.myapplication.data.PostJoinResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

// controller 전부 다 넣기
interface RetrofitApi {
    @POST("join")
    suspend fun join(@Body postJoinRequestDto: PostJoinRequestDto): PostJoinResponseDto
}
