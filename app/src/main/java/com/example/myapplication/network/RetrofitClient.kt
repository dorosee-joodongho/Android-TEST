package com.example.myapplication.network

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.example.com/"
    private var sharedPreferences: SharedPreferences? = null

    // Retrofit 초기화 함수
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
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
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

// RetrofitApi 예제 : API 엔드 포인트 전부 정의하기
/*
interface RetrofitApi {
    @GET("user/profile")
    suspend fun getUserProfile(): UserProfile
}
*/

// Service 예제
/*
class UserService(private val retrofitApi: RetrofitApi) {

    suspend fun getUserProfile(): Result<UserProfile> {
        return try {
            val profile = retrofitApi.getUserProfile()
            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

 */

// Activity 예제
/*
class MainActivity : AppCompatActivity() {

    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RetrofitClient에서 RetrofitApi 생성
        val retrofitApi = RetrofitClient.instance.create(RetrofitApi::class.java)

        // UserService 초기화
        userService = UserService(retrofitApi)

        // 사용자 프로필 가져오기
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        lifecycleScope.launch {
            val result = userService.getUserProfile()
            result.onSuccess { profile ->
                Log.d("MainActivity", "User Profile: $profile")
                findViewById<TextView>(R.id.userNameTextView).text = profile.name
            }.onFailure { e ->
                Log.e("MainActivity", "Error: ${e.message}")
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
 */