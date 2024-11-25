package com.example.myapplication.network

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private var sharedPreferences: SharedPreferences? = null

    // Retrofit 초기화 함수
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = sharedPreferences?.getString("auth_token", null)
                val request = chain.request().newBuilder().apply {
                    token?.let { addHeader("Authorization", "Bearer $it") }
                }.build()
                chain.proceed(request)
            }
            .build()
    }
    // Retrofit 인스턴스
    val instance: RetrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitApi::class.java)
    }
}


//object RetrofitClient {
//
//    private const val BASE_URL = "http://localhost:8080/"
//    private var sharedPreferences: SharedPreferences? = null
//
//    // Gson을 사용할 때 Double을 Long으로 변환하는 디시리얼라이저
//    val longDeserializer: JsonDeserializer<Long> = JsonDeserializer { json: JsonElement, typeOfT: Type, context ->
//        json.asJsonPrimitive.asDouble.toLong()  // Double을 Long으로 변환
//    }
//
//    // Gson 빌더에서 디시리얼라이저 등록
//    val gson: Gson = GsonBuilder()
//        .registerTypeAdapter(Long::class.java, longDeserializer)  // Long 타입에 대한 변환 설정
//        .registerTypeAdapter(Int::class.java, JsonDeserializer { json, _, _ ->
//            json.asJsonPrimitive.asDouble.toInt()  // Double을 Int로 변환
//        })
//        .create()
//
//    // Retrofit 인스턴스 생성
//    val instance: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create(gson))  // GsonConverterFactory로 커스텀 Gson 사용
//            .build()
//    }
//
//    private val client: OkHttpClient by lazy {
//        OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val token = sharedPreferences?.getString("auth_token", null)
//                val request = chain.request().newBuilder().apply {
//                    token?.let { addHeader("Authorization", "Bearer $it") }
//                }.build()
//                chain.proceed(request)
//            }
//            .build()
//    }
//
//    // Authorization Token 반환
//    fun getAuthToken(): String? {
//        return sharedPreferences?.getString("auth_token", null)
//    }
//}
