package com.example.myapplication.service

import com.example.myapplication.data.User

class AuthService {

    fun getCurrentUser() = User(1 , "userName", "010-1234-1234" , "admin@gmail.com", "https://png.pngtree.com/png-vector/20191009/ourmid/pngtree-user-icon-png-image_1796659.jpg")

    fun addMember(name: String, phone: String, email: String, password: String, callback: (Boolean) -> Unit) {
        // 회원 정보 저장
        callback(true) // 성공 여부 반환
    }

    fun updateMember(name: String, phone: String, password: String, callback: (Boolean) -> Unit) {
        // 서버와의 통신 로직 예제
        callback(true) // 성공 여부 반환
    }
}