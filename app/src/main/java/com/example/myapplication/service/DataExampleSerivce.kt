package com.example.myapplication.service

import com.example.myapplication.data.Member
import com.example.myapplication.data.PostJoinRequestDto
import com.example.myapplication.network.RetrofitApi

class DataExampleService(private val retrofitApi: RetrofitApi) {

    // GET request example
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

    // POST request example
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

}