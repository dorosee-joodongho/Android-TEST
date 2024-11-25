package com.example.myapplication.data

data class Member(
    val memberID : Long,
    val memberName : String,
    val memberPhone: String,
    val memberEmail: String,
    val userImage : String,
)

data class LoginResponse(
    val isMember: Int,
    val token: String
)

data class PostJoinRequestDto (
    val name: String,
    val phone: String,
    val email: String,
    val password: String
)