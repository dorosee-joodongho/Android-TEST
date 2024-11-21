package com.example.myapplication.service

import com.example.myapplication.data.Member

class MemberService {

    fun getCurrentUser() = Member(1 , "userName", "010-1234-1234" , "admin@gmail.com", "https://png.pngtree.com/png-vector/20191009/ourmid/pngtree-user-icon-png-image_1796659.jpg")

    fun login(email: String, password: String, callback: (Member, isMember: Boolean) -> Unit) {
        callback(getCurrentUser(), true); // 로그인 정보, 고객인지 식당인지
    }

    fun addMember(name: String, phone: String, email: String, password: String, callback: (Member?) -> Unit) {
        // 회원 정보 저장
        callback(getCurrentUser()) // 생성된 Member 객체 반환
    }

    fun updateMember(name: String, phone: String, password: String, callback: (Member?) -> Unit) {
        // 회원 정보 수정
        callback(getCurrentUser()) // 업데이트 된 Member 객체 반환
    }
}