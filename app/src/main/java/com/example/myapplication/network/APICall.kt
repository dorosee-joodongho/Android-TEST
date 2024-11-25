package com.example.myapplication.network

//API 호출시 사용 바람 apiService 의존성(DI) 해서
class APICall(
    private val apiService: APIService
) {
    val BASEURL = "http://localhost:8080/"

    fun <T> apiCall(
        method: String,
        apiEndpoint: String,
        requestBody: Any? = null,
        queryParam: String? = null
    ): T? {
        return try {
            val response: T = when (method.uppercase()) {
                "GET" -> {
                    if (queryParam != null) {
                        apiService.getApi(
                            BASEURL + apiEndpoint,
                            RetrofitClient.getAuthToken(),
                            queryParam
                        )
                    } else {
                        apiService.getApi(
                            BASEURL + apiEndpoint,
                            RetrofitClient.getAuthToken(),
                            null
                        )
                    }
                }
                "POST" -> {
                    if (requestBody == null) throw IllegalArgumentException("BODY 값 넣어서 재 실행 바람")
                    apiService.postApi(
                        BASEURL + apiEndpoint,
                        RetrofitClient.getAuthToken(),
                        requestBody
                    )
                }
                "PUT" -> {
                    if (requestBody == null) throw IllegalArgumentException("BODY 값 넣어서 재 실행 바람")
                    apiService.putApi(
                        BASEURL + apiEndpoint,
                        RetrofitClient.getAuthToken(),
                        requestBody
                    )
                }
                "DELETE" -> {
                    apiService.deleteApi(
                        BASEURL + apiEndpoint,
                        RetrofitClient.getAuthToken()
                    )
                }
                else -> throw IllegalArgumentException("해당 함수 지원 안함: $method")
            }

            response // 응답 객체 반환
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
