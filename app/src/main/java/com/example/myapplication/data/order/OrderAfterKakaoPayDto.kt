package com.example.myapplication.data.order

class OrderAfterKakaoPayDto(
    val tid: String? = null,
    // 요청한 클라이언트가 모바일 앱인 경우
    val next_redirect_app_url: String? = null,

    // 요청한 클라이언트가 모바일 웹인 경우
    val next_redirect_mobile_url: String? = null,

    // 요청한 클라이언트가 pc 인 경우
    val next_redirect_pc_url: String? = null,

    // 카카오페이 결제 화면으로 이동하는 내부 서비스용
    val android_app_scheme: String? = null,

    // 카카오페이 결제 화면으로 이동하는 IOS 앱 스킴 - 내부 서비스용
    val ios_app_scheme: String? = null,

    val created_at: String? = null
) {
    // toString() 메서드를 오버라이드하여 객체의 속성값을 출력
    override fun toString(): String {
        return "OrderAfterKakaoPayDto(tid=$tid, next_redirect_app_url=$next_redirect_app_url, " +
                "next_redirect_mobile_url=$next_redirect_mobile_url, next_redirect_pc_url=$next_redirect_pc_url, " +
                "android_app_scheme=$android_app_scheme, ios_app_scheme=$ios_app_scheme, created_at=$created_at)"
    }
}