package com.example.myapplication.service

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.Member
import com.example.myapplication.data.PostJoinRequestDto
import com.example.myapplication.data.PostLoginRequestDto
import com.example.myapplication.network.RetrofitApi
import com.example.myapplication.utils.TransRequestBody

class MemberService(context: Context, private val retrofitApi: RetrofitApi) {
    private val sharedPreferences: SharedPreferences = context.applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    // 현재 사용자 정보를 반환하는 함수 (예시)
    fun getCurrentUser() = Member(1, "userName", "010-1234-1234", "admin@gmail.com", "https://png.pngtree.com/png-vector/20191009/ourmid/pngtree-user-icon-png-image_1796659.jpg")

    suspend fun getMember() : Member? {
        try {
            val response = retrofitApi.getMember()

            return Member(
                memberID = response.memberId,
                memberEmail = response.memberEmail,
                memberName = response.memberName,
                memberPhone = response.memberPhone,
                userImage = "https://png.pngtree.com/png-vector/20191009/ourmid/pngtree-user-icon-png-image_1796659.jpg"
            )

        }catch (e: Exception) {
            println("회원 정보를 가져오는 중 오류 발생 : ${e.message}")
            return null
        }
    }

    // 로그인 처리 함수
    suspend fun login(email: String, password: String): Boolean? {
        val loginInfo = PostLoginRequestDto(
            email = email,
            password = password
        )

        return try {
            val response = retrofitApi.login(loginInfo)
            println("로그인 응답: ${response}")
            println("저장된 토큰 : ${getAuthToken()}")
            saveAuthToken(response.token)
            if (response.isMember) {
                return true // 고객 로그인
            } else {
                return false // 식당 로그인
            }
        } catch (e: Exception) {
            println("로그인 중 오류 발생: ${e.message}")
            null
        }
    }

    // 회원가입
    suspend fun saveMember(name: String, phone: String, email: String, password: String): Boolean {
        val joinInfo = PostJoinRequestDto(
            name = name,
            phone = phone,
            email = email,
            password = password
        )

        return try {
            val response = retrofitApi.join(joinInfo)
            println("회원가입 응답 코드: ${response.code}")
            response.code == "SU" // 성공 여부 확인
        } catch (e: Exception) {
            println("회원가입 중 오류 발생: ${e.message}")
            false
        }
    }

    // 회원 정보 수정
    suspend fun updateMember(memberId: Long, memberName: String, memberPhone: String, memberPassword: String) : Boolean {
        try {
            val id = TransRequestBody.prepareStringPart(memberId.toString())
            val name = TransRequestBody.prepareStringPart(memberName)
            val phone = TransRequestBody.prepareStringPart(memberPhone)
            val password = TransRequestBody.prepareStringPart(memberPassword)

            val response = retrofitApi.updateMember(id, name, phone, password)

            println("회원 정보 수정 완료 $response")
            return true

        }catch (e: Exception) {
            println("회원 정보를 가져오는 중 오류 발생 : ${e.message}")
            return false
        }
    }

    // 토큰을 SharedPreferences에 저장
    private fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    // SharedPreferences에서 토큰 가져오기
    fun getAuthToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }
}
