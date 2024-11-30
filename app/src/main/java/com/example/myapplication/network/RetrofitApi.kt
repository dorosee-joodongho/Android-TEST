package com.example.myapplication.network

import com.example.myapplication.data.DietResponse
import com.example.myapplication.data.GetStoreMenuListResponseDto
import com.example.myapplication.data.LoginResponse
import com.example.myapplication.data.MemberDto
import com.example.myapplication.data.PostJoinRequestDto
import com.example.myapplication.data.PostJoinResponseDto
import com.example.myapplication.data.PostLoginRequestDto
import com.example.myapplication.data.menu.MenuResponse
import com.example.myapplication.data.store.MenuParentStoreEntity
import com.example.myapplication.data.store.StoreResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

// controller 전부 다 넣기
interface RetrofitApi {
    @POST("join")
    suspend fun join(@Body postJoinRequestDto: PostJoinRequestDto): PostJoinResponseDto

    @POST("login")
    suspend fun login(@Body postLoginRequestDto: PostLoginRequestDto): LoginResponse

    @GET("member")
    suspend fun getMember(): MemberDto

    @Multipart
    @PUT("update")
    suspend fun updateMember(
        @Part("memberId") memberId: RequestBody,
        @Part("memberName") memberName: RequestBody,
        @Part("memberPhone") memberPhone: RequestBody,
        @Part("password") password: RequestBody,
    ): ResponseBody

    @GET("diet")
    suspend fun getDietList(): DietResponse

    @Multipart
    @POST("menu/save")
    suspend fun saveMenu(
        @Part("menuId") menuId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("calorie") calorie: RequestBody,
        @Part("carbs") carbs: RequestBody,
        @Part("protein") protein: RequestBody,
        @Part("fat") fat: RequestBody,
        @Part("isSoldOut") isSoldOut: RequestBody,
        @Part menuImg: MultipartBody.Part?
    ): ResponseBody

    @GET("store/list")
    suspend fun getStoreMenuList(): GetStoreMenuListResponseDto

    @DELETE("menu/delete/{menuId}")
    suspend fun deleteMenu(@Path("menuId") menuId: Long)

    @Multipart
    @PUT("menu/update/{menuId}")
    suspend fun updateMenu(
        @Path("menuId") menuId: Long,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("calorie") calorie: RequestBody,
        @Part("carbs") carbs: RequestBody,
        @Part("protein") protein: RequestBody,
        @Part("fat") fat: RequestBody,
        @Part("isSoldOut") isSoldOut: RequestBody,
        @Part menuImg: MultipartBody.Part?
    ): ResponseBody


    //추천 메뉴 목록 가지고오기
    @GET("recommendation")
    suspend fun getStoreRecommendationMenuList(): MenuResponse

    @GET("store")
    suspend fun getStoreList() : StoreResponse
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