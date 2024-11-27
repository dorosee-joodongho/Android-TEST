package com.example.myapplication.data

data class Member(
    val memberID : Long,
    val memberName : String,
    val memberPhone: String,
    val memberEmail: String,
    val userImage : String,
)

data class PostLoginRequestDto(
    val email: String,
    val password: String
)

data class LoginResponse(
    val isMember: Boolean,
    val token: String
)

data class PostJoinRequestDto (
    val name: String,
    val phone: String,
    val email: String,
    val password: String
)

data class MemberDto(
    val memberId: Long,
    val memberName: String,
    val memberPhone: String,
    val memberEmail: String
)

data class PostJoinResponseDto(
    val code: String
)
