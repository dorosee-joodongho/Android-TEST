package com.example.myapplication.service

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.Member
import com.example.myapplication.data.LoginResponse
import com.example.myapplication.network.APICall
import com.example.myapplication.network.APIService
import com.example.myapplication.network.RetrofitClient

class MemberService(context: Context) {
    private val apiService = RetrofitClient.instance.create(APIService::class.java)
    private val apiCall = APICall(apiService)
    private val sharedPreferences: SharedPreferences = context.applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    // 현재 사용자 정보를 반환하는 함수 (예시)
    fun getCurrentUser() = Member(1, "userName", "010-1234-1234", "admin@gmail.com", "https://png.pngtree.com/png-vector/20191009/ourmid/pngtree-user-icon-png-image_1796659.jpg")

    suspend fun getMember() : Member {
        return getCurrentUser()
    }

    // 로그인 처리 함수
    suspend fun login(email: String, password: String): Boolean? {
        val requestBody = mapOf(
            "email" to email,
            "password" to password
        )

        return try {
            // 로그인 API 호출
            val response: LoginResponse? = apiCall.apiCall(
                method = "POST",
                apiEndpoint = "/login",
                requestBody = requestBody,
            )

            // 응답이 null이면 실패
            response?.let {
                if (it.isMember == 1) {
                    val token = it.token
                    token?.let {
                        saveAuthToken(it) // 토큰 저장
                    }
                    true // 고객 로그인
                } else {
                    false // 가게 로그인
                }
            } // 응답이 null일 경우 실패
        } catch (e: Exception) {
            println("로그인 중 오류 발생: ${e.message}")
            null // 로그인 실패
        }
    }

    // 회원가입
    suspend fun saveMember(name: String, phone: String, email: String, password: String) : Boolean {
        return true
    }

    // 회원 정보 수정
    suspend fun updateMember(name: String, phone: String, password: String) : Boolean {
        return true
    }

    // 토큰을 SharedPreferences에 저장
    private fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)  // 일관성 있는 키 이름 사용
        editor.apply()
    }

    // SharedPreferences에서 토큰 가져오기
    fun getAuthToken(): String? {
        return sharedPreferences.getString("auth_token", null)  // 동일한 키 이름 사용
    }
}
